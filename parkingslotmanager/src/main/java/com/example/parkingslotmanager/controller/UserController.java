package com.example.parkingslotmanager.controller;

import com.example.parkingslotmanager.model.entity.User;
import com.example.parkingslotmanager.model.dto.UserDto;
import com.example.parkingslotmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDto userDto) {

            User newUser = userService.registerUser(userDto);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Integer id) {

        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

}