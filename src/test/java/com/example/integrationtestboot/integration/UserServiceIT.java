package com.example.integrationtestboot.integration;

import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.entity.User;
import com.example.integrationtestboot.repository.UserRepository;
import com.example.integrationtestboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
                Role.ADMIN, "a", "a");
        createUserAndRegister(LocalDate.of(1980, 10, 12), "Admin@1980",
                Role.ADMIN, "b", "b");
        createUserAndRegister(LocalDate.of(1999, 11, 21), "Admin@1999",
                Role.ADMIN, "c", "c");

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

    @Test
    public void whenFindFirst4Users_thenSuccess() {
        // Method to test
        List<User> users = userService.findFirst4Users();

        // Assertions
        assertEquals(4, users.size(), "Amount of users mor or less than 4");
    }

    @Test
    public void whenFindUsersSortedByBirthDate_thenSuccess() {
        // Act
        List<User> users = userService.findUsersWithDynamicSorting("birthDate", "ASC");

        // Assert
        assertEquals(4, users.size(), "There should be exactly 4 users returned");
        assertTrue(isSortedByBirthDate(users), "Users should be sorted by birth date in ascending order");
    }

    private boolean isSortedByBirthDate(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            if (users.get(i).getBirthDate().isAfter(users.get(i + 1).getBirthDate())) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void whenFindUsersSortedByBirthDateAndFullName_thenSuccess() {
        // Arrange
        Sort sort = Sort.by(
                Sort.Order.asc("birthDate"),
                Sort.Order.asc("firstname"),
                Sort.Order.asc("lastname")
        );

        // Act
        List<User> sortedUsers = userService.findUsersWithDynamicSorting(sort);

        // Assert
        assertEquals(4, sortedUsers.size(), "There should be exactly 4 users returned");
        assertTrue(isSortedByBirthDateFirstNameAndLastName(sortedUsers), "Users should be sorted by birth date, first name, and last name in ascending order");
    }

    private boolean isSortedByBirthDateFirstNameAndLastName(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            User current = users.get(i);
            User next = users.get(i + 1);
            int birthDateComparison = current.getBirthDate().compareTo(next.getBirthDate());
            if (birthDateComparison > 0 ||
                    (birthDateComparison == 0 && current.getFirstname().compareTo(next.getFirstname()) > 0) ||
                    (birthDateComparison == 0 && current.getFirstname().equals(next.getFirstname()) &&
                            current.getLastname().compareTo(next.getLastname()) > 0)) {
                return false;
            }
        }
        return true;
    }

    /////////

    @Test
    public void whenFilterUsersByRoleAndSortDynamically_thenSuccess() {
        // Arrange
        Sort sort = Sort.by(Sort.Direction.ASC, "lastname");
        int page = 0;
        int size = 10;

        // Act
        Page<User> result = userService.findUsersFilteredByRoleWithDynamicSortingAndPagination(
                Role.USER, sort, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(size, result.getSize());
        assertTrue(result.getContent().stream().allMatch(user -> user.getRole() == Role.USER));
        assertTrue(isSortedByLastName(result.getContent()));
    }

    // Test sorting by another attribute
    @Test
    public void whenFilterUsersByRoleAndSortByFirstName_thenSuccess() {
        // Arrange
        Sort sort = Sort.by(Sort.Direction.ASC, "firstname");
        int page = 0;
        int size = 10;

        // Act
        Page<User> result = userService.findUsersFilteredByRoleWithDynamicSortingAndPagination(
                Role.USER, sort, page, size);

        // Assert
        assertNotNull(result);
        assertTrue(isSortedByFirstName(result.getContent()));
    }

    // Test sorting and pagination
    @Test
    public void whenFilterUsersByRoleAndPaginate_thenSuccess() {
        // Arrange
        Sort sort = Sort.by(Sort.Direction.ASC, "firstname");
        int page = 1;
        int size = 5;

        // Act
        Page<User> result = userService.findUsersFilteredByRoleWithDynamicSortingAndPagination(
                Role.USER, sort, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(size, result.getSize());
        assertEquals(page, result.getNumber());
    }

    private boolean isSortedByLastName(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            User current = users.get(i);
            User next = users.get(i + 1);
            if (current.getLastname().compareTo(next.getLastname()) > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByFirstName(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            User current = users.get(i);
            User next = users.get(i + 1);
            if (current.getFirstname().compareTo(next.getFirstname()) > 0) {
                return false;
            }
        }
        return true;
    }


}
