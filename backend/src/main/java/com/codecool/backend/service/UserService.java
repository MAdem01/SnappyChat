package com.codecool.backend.service;

import com.codecool.backend.dto.NewUserDTO;
import com.codecool.backend.dto.UserDTO;
import com.codecool.backend.repository.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers(){
    }

    public long createUser(NewUserDTO newUserDTO) {
    }
}
