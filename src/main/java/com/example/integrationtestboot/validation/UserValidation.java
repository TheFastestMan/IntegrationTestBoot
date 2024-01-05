package com.example.integrationtestboot.validation;

import com.example.integrationtestboot.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
public class UserValidation {
    public  void validateUserDTO(UserDTO userDTO) {

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        if (!StringUtils.hasText(userDTO.getName())) {
            throw new IllegalArgumentException("User name is required");
        }
    }
}
