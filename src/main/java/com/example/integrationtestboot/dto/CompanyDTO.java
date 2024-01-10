package com.example.integrationtestboot.dto;

import jdk.jfr.Enabled;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ToString
@EqualsAndHashCode
public class CompanyDTO {
    private Long id;
    private String name;
    private UserDTO user;
}
