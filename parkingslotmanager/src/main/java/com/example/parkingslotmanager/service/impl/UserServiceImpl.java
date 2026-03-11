package com.example.parkingslotmanager.service.impl;

import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.model.dto.UserDto;
import com.example.parkingslotmanager.repository.UserRepository;
import com.example.parkingslotmanager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) { // Constructor Injection
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email address is already registered.");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setContact(userDto.getContact());
        user.setRole("User");
        user.setJoinDate(LocalDateTime.now());
        return userRepository.save(user);
    }



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}