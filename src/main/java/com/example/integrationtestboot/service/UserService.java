package com.example.integrationtestboot.service;

import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.entity.User;
import com.example.integrationtestboot.listener.AccessType;
import com.example.integrationtestboot.listener.EventEntity;
import com.example.integrationtestboot.mapper.UserMapper;
import com.example.integrationtestboot.repository.UserRepository;
import com.example.integrationtestboot.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserValidation userValidation;


    @Transactional
    public Long registerUser(UserDTO userDTO) {
      userValidation.validateUserDTO(userDTO);
        User user = userMapper.userDTOToUser(userDTO);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.CREATE, "Before"));
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.CREATE, "After"));
        return user.getId();
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.CREATE, "Before"));
        UserDTO userDTO = userMapper.userToUserDTO(user.get());
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.CREATE, "Before"));
        return Optional.of(userDTO);
    }

    public List<User> findAdminsBornBetween1980And1990() {
        LocalDate startDate = LocalDate.of(1980, 1, 1);
        LocalDate endDate = LocalDate.of(1990, 12, 31);
        return userRepository.findByRoleAndBirthDateBetween(Role.ADMIN, startDate, endDate);
    }

    public List<User> findFirst4Users() {
        return userRepository.findFirst4ByOrderByIdDesc();
    }

    public List<User> findUsersWithDynamicSorting(String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(0, 4, sort);
        return userRepository.findAll(pageable).getContent();
    }

    public List<User> findUsersWithDynamicSorting(Sort sort) {
        Pageable pageable = PageRequest.of(0, 4, sort);
        return userRepository.findAll(pageable).getContent();
    }

    public Page<User> findUsersFilteredByRoleWithDynamicSortingAndPagination(
            Role role, Sort sort, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findByRole(role, pageable);
    }

}
