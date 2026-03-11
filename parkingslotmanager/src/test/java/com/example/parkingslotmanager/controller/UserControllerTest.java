package com.example.parkingslotmanager.controller;

import com.example.parkingslotmanager.model.dto.UserDto;
import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // Focuses test only on UserController
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Spring will inject a mock instance of the service
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_Success_Returns201() throws Exception {
        // Arrange
        UserDto inputDto = new UserDto();
        inputDto.setName("Test User");
        inputDto.setEmail("test@api.com");

        User savedUser = new User();
        savedUser.setUserId(1);

        // Mock the service call
        when(userService.registerUser(any(UserDto.class))).thenReturn(savedUser);

        // Act & Assert: Simulate a PUT request
        mockMvc.perform(put("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated()); // Expect HTTP 201
    }

    @Test
    void registerUser_Conflict_Returns409() throws Exception {
        // Arrange
        UserDto inputDto = new UserDto();
        inputDto.setName("Duplicate User");

        // Mock the service to throw an exception (e.g., email already exists)
        when(userService.registerUser(any(UserDto.class))).thenThrow(new IllegalStateException("Email exists."));

        // Act & Assert
        mockMvc.perform(put("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isConflict()); // Expect HTTP 409
    }
}