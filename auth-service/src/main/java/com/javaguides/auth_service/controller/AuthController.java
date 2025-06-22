package com.javaguides.auth_service.controller;

import com.javaguides.auth_service.dto.JwtRequest;
import com.javaguides.auth_service.dto.JwtResponse;
import com.javaguides.auth_service.dto.UserDto;
import com.javaguides.auth_service.exception.BadApiRequestException;
import com.javaguides.auth_service.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserDetailsService userDetailsService;
    private ModelMapper mapper;
    private AuthenticationManager authenticationManager;
    private JwtHelper jwtHelper;

    public AuthController(UserDetailsService userDetailsService, ModelMapper mapper, AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping("message")
    public String getMessage(){
        return "hello Abhishek";
    }

    @GetMapping("/name")
    ResponseEntity<UserDto> getCurrentUser(Principal principal){
        String name = principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails, UserDto.class);

        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .userDto(userDto)
                .build();

        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new BadApiRequestException("Invalid Username or Password !!");
        }
    }
}
