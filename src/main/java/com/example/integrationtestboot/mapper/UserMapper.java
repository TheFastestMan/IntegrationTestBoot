package com.example.integrationtestboot.mapper;

import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setRole(user.getRole());
        dto.setName(user.getName());
        return dto;
    }

    public User userDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setRole(dto.getRole());
        user.setName(dto.getName());
        return user;
    }
}
