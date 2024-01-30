package com.example.integrationtestboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@EqualsAndHashCode
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstname;

    private String lastname;

    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Company> companies;

}
