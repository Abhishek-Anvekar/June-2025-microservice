package com.javaguides.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    private static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/api/user"
    );

    private static final Map<String, List<String>> protectedEndpointsWithRoles = Map.of(
            "/api/user/delete/**", List.of("ROLE_ADMIN"),
            "/employees/delete/**", List.of("ROLE_ADMIN")
//            "/employees/**", List.of("ROLE_NORMAL", "ROLE_ADMIN")
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private Key getSigningKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getURI().getPath();
        System.out.println("[JwtAuth] Incoming path: " + requestPath);

        if (isPublicEndpoint(requestPath)) {
            System.out.println("[JwtAuth] Public endpoint — skipping auth.");
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[JwtAuth] Missing or malformed Authorization header.");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            System.out.println("[JwtAuth] Role from token: " + role);

            if (!isAuthorized(requestPath, role)) {
                System.out.println("[JwtAuth] Access denied for role: " + role);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            exchange = exchange.mutate()
                    .request(r -> r.header("X-User-Role", role))
                    .build();

        } catch (JwtException e) {
            System.out.println("[JwtAuth] JWT validation error: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isPublicEndpoint(String path) {
        return openApiEndpoints.stream().anyMatch(path::startsWith);
    }

    private boolean isAuthorized(String path, String role) {
        for (Map.Entry<String, List<String>> entry : protectedEndpointsWithRoles.entrySet()) {
            String pattern = entry.getKey();
            List<String> allowedRoles = entry.getValue();
            if (pathMatcher.match(pattern, path)) {
                return allowedRoles.contains(role);
            }
        }
        return true;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}