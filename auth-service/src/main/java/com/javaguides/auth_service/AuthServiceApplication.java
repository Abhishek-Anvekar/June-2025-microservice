package com.javaguides.auth_service;

import com.javaguides.auth_service.entity.Role;
import com.javaguides.auth_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Value("${normal.role.id}")
	private String role_normal_id;
	@Value("${admin.role.id}")
	private String role_admin_id;

	public static void main(String[] args) {SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("My password :"+passwordEncoder.encode("Password123"));
		try {
			Role role_admin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			Role role_normal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
			roleRepository.saveAll(Arrays.asList(role_admin,role_normal));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
