package com.example.parkingslotmanager.service;

import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.model.dto.UserDto;
import java.util.List;

public interface UserService {
    User registerUser(UserDto userDto);
    List<User> getAllUsers();
    User findUserById(Integer id);
}