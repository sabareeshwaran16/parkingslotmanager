package com.example.parkingslotmanager.service.impl;

import com.example.parkingslotmanager.model.dto.UserDto;
import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUserDto = new UserDto();
        testUserDto.setName("Test User");
        testUserDto.setEmail("test@example.com");
        testUserDto.setContact("1234567890");
    }

    @Test
    void registerUser_Success() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User savedUser = i.getArgument(0);
            savedUser.setUserId(1); // Simulate ID generation
            return savedUser;
        });

        // Act
        User registeredUser = userServiceImpl.registerUser(testUserDto);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_EmailAlreadyExists_ThrowsException() {
        // Arrange
        User existingUser = new User();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            userServiceImpl.registerUser(testUserDto);
        });

        // Verify save method was never called
        verify(userRepository, never()).save(any(User.class));
    }
}