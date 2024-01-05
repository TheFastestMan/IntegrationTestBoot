package com.example.integrationtestboot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ToString
public class CompanyDTO {
    private Long id;
    private String name;
    private UserDTO user;
}
