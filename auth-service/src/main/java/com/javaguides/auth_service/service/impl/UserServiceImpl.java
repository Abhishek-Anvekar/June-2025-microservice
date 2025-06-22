package com.javaguides.auth_service.service.impl;

import com.javaguides.auth_service.dto.UserDto;
import com.javaguides.auth_service.entity.Role;
import com.javaguides.auth_service.entity.User;
import com.javaguides.auth_service.exception.ResourceNotFoundException;
import com.javaguides.auth_service.repository.RoleRepository;
import com.javaguides.auth_service.repository.UserRepository;
import com.javaguides.auth_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Value("${normal.role.id}")
    private String normalRoleId;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findById(normalRoleId).get();
        userDto.getRoles().add(role);
        User user = mapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with given id: " + userId)
        );
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserById(long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with given id: " + userId)
        );
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser = userRepository.save(user);
        return mapper.map(updatedUser, UserDto.class);
    }

    @Override
    public String deleteUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with given id: " + userId)
        );
        userRepository.deleteById(userId);
        return "User deleted successfully..";
    }
}
