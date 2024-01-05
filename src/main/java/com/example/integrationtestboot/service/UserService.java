package com.example.integrationtestboot.service;

import com.example.integrationtestboot.dto.UserDTO;
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
        User user = userRepository.findUserById(id);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.CREATE, "Before"));
        UserDTO userDTO = userMapper.userToUserDTO(user);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.CREATE, "Before"));
        return Optional.of(userDTO);
    }

    public String updateUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.UPDATE, "Before"));
        boolean userVar = userRepository.update(user);
        applicationEventPublisher.publishEvent(new EventEntity(user,
                AccessType.UPDATE, "After"));
        if (userVar == true) {
            return "User has updated";
        }
        return "User has NOT updated!";
    }

    public String deleteUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            applicationEventPublisher.publishEvent(new EventEntity(user,
                    AccessType.UPDATE, "Before"));
            boolean userVar = userRepository.delete(id);
            applicationEventPublisher.publishEvent(new EventEntity(user, AccessType.DELETE, "After"));
            return userVar ? "User has deleted" : "User has NOT deleted!";
        }
        return "User has NOT deleted!";
    }
}
