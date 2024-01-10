package com.example.integrationtestboot.dto;

import com.example.integrationtestboot.entity.Company;
import com.example.integrationtestboot.entity.Role;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@Component
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class
UserDTO {
    private Long id;
    private Role role;
    private LocalDate birthDate;
    private String email;
    private String firstname;

    private String lastname;
    private List<Company> owner;

}
