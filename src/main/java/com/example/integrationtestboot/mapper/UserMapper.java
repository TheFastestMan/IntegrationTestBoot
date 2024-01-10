package com.example.integrationtestboot.mapper;

import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setBirthDate(user.getBirthDate());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setLastname(user.getLastname());
        dto.setFirstname(user.getFirstname());
        return dto;
    }

    public User userDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setRole(dto.getRole());
        user.setBirthDate(dto.getBirthDate());
        user.setEmail(dto.getEmail());
        user.setLastname(dto.getLastname());
        user.setFirstname(dto.getFirstname());
        return user;
    }
}
