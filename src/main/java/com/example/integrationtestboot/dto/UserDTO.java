package com.example.integrationtestboot.dto;

import com.example.integrationtestboot.entity.Company;
import com.example.integrationtestboot.entity.Role;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Setter
@Getter
@Component
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private Role role;
    private String name;
    private List<Company> owner;

}
