package com.example.integrationtestboot.integration;

import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.entity.User;
import com.example.integrationtestboot.repository.UserRepository;
import com.example.integrationtestboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceIT {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    public void saveTest() {
        UserDTO newUser = new UserDTO();
        LocalDate specificDate = LocalDate.of(2020, 1, 1);
        newUser.setEmail("Test User");
        newUser.setFirstname("In");
        newUser.setLastname("Out");
        newUser.setBirthDate(specificDate);
        newUser.setRole(Role.USER);

        Long userId = userService.registerUser(newUser);

        Optional<User> retrieve = userRepository.findById(userId);

        assertNotNull(retrieve);
        assertEquals("Test User", retrieve.get().getEmail());
        assertEquals("In", retrieve.get().getFirstname());
        assertEquals("Out", retrieve.get().getLastname());
        assertEquals(specificDate, retrieve.get().getBirthDate());
        assertEquals(Role.USER, retrieve.get().getRole());

    }

    @Test
    public void whenFindAdminsBornBetween1980And1990_thenSuccess() {
        // Create and register users
        createUserAndRegister(LocalDate.of(1990, 2, 10), "Admin@1990",
                Role.ADMIN,"a", "a");
        createUserAndRegister(LocalDate.of(1980, 10, 12), "Admin@1980",
                Role.ADMIN,"b","b");
        createUserAndRegister(LocalDate.of(1999, 11, 21), "Admin@1999",
                Role.ADMIN,"c","c");

        // Method to test
        List<User> admins = userService.findAdminsBornBetween1980And1990();

        // Assertions
        assertTrue(admins.stream().allMatch(user -> user.getRole() == Role.ADMIN),
                "All retrieved users should be admins");
        assertTrue(admins.stream().allMatch(user -> user.getBirthDate().getYear() >= 1980 &&
                        user.getBirthDate().getYear() <= 1990),
                "All admins should be born between 1980 and 1990");
        assertEquals(8, admins.size(), "There should be only one admin born between 1980 and 1990");
    }

    private void createUserAndRegister(LocalDate birthDate, String email, Role role, String firstname, String lastname) {
        UserDTO newUser = new UserDTO();
        newUser.setEmail(email);
        newUser.setBirthDate(birthDate);
        newUser.setRole(role);
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        userService.registerUser(newUser);
    }



}
