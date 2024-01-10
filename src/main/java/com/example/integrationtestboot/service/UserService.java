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

}
