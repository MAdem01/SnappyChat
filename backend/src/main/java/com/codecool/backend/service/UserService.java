package com.codecool.backend.service;

import com.codecool.backend.dto.NewUserDTO;
import com.codecool.backend.dto.UserDTO;
import com.codecool.backend.repository.UserRepository;
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
        List<UserEntity> users = userRepository.findAll();

        if(users.isEmpty()){
            return null;
        }

        return users.stream().map(userEntity -> new UserDTO(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getEmail())).toList();
    }

    public long createUser(NewUserDTO newUserDTO) {
        UserEntity userEntity = new UserEntity(
                newUserDTO.username(),
                newUserDTO.password(),
                newUserDTO.email()
        );

        return userRepository.save(userEntity).getId();
    }
}
