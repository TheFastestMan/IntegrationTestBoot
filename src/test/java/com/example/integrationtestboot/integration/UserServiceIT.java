package com.example.integrationtestboot.integration;

import com.example.integrationtestboot.IntegrationTestBootApplication;
import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.entity.User;
import com.example.integrationtestboot.repository.UserRepository;
import com.example.integrationtestboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceIT {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    public void testRegisterUser() {
        UserDTO newUser = new UserDTO();
        newUser.setName("Test User");
        newUser.setRole(Role.USER);

        // Stub the save operation
        doNothing().when(userRepository).save(userCaptor.capture());

        userService.registerUser(newUser);

        verify(userRepository).save(any(User.class));

        User capturedUser = userCaptor.getValue();
        assertNotNull(capturedUser);
        assertEquals("Test User", capturedUser.getName());
        assertEquals(Role.USER, capturedUser.getRole());
    }

    @Test
    void test1() {
        var actualResult = userService.findUserById(1L);

        assertTrue(actualResult.isPresent());

        var expectedResult = new UserDTO(1L, Role.ADMIN, "Name12", null);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }

}
