package com.example.integrationtestboot;

import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class IntegrationTestBootApplication {

    public static void main(String[] args) {
        var context =  SpringApplication.run(IntegrationTestBootApplication.class, args);

        UserService userService = context.getBean(UserService.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setRole(Role.ADMIN);
        userDTO.setName("Name12");

       // Long userId = userService.registerUser(userDTO);

       Optional<UserDTO> userDTO1 = userService.findUserById(1L);

        System.out.println(userDTO1);

    }

}
