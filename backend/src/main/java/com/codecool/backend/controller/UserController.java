package com.codecool.backend.controller;

import com.codecool.backend.dto.NewUserDTO;
import com.codecool.backend.dto.UserDTO;
import com.codecool.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public long createUser(@RequestBody NewUserDTO newUserDTO){
        return userService.createUser(newUserDTO);
    }
}
