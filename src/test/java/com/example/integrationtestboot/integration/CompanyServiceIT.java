package com.example.integrationtestboot.integration;

import com.example.integrationtestboot.dto.CompanyDTO;
import com.example.integrationtestboot.dto.UserDTO;
import com.example.integrationtestboot.entity.Company;
import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.entity.User;
import com.example.integrationtestboot.repository.CompanyRepository;
import com.example.integrationtestboot.repository.UserRepository;
import com.example.integrationtestboot.service.CompanyService;
import com.example.integrationtestboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CompanyServiceIT {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        Stream.of("Apple", "Amazon", "Microsoft", "Alphabet")
                .map(name -> {
                    Company company = new Company();
                    company.setName(name);
                    return company;
                })
                .forEach(companyRepository::save);
    }

    @Test
    public void testUpdateCompany() {
        // Setup
        User user = new User();
        user.setEmail("Ivan");
        user.setRole(Role.USER);

        // Setup
        Company initialCompany = new Company();
        initialCompany.setName("Original Company Name");
        initialCompany.setUser(user);
        initialCompany = companyRepository.save(initialCompany);

        // Setup
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(initialCompany.getId());
        companyDTO.setName("Updated Company Name");

        companyService.updateCompany(companyDTO.getId(), companyDTO);
        Company updatedCompany = companyRepository.findById(initialCompany.getId()).orElse(null);

        assertNotNull(updatedCompany);
        assertEquals("Updated Company Name", updatedCompany.getName());

    }

    @Test
    public void whenDeletingCompaniesStartingWithAThenTheyShouldBeRemoved() {
        companyService.deleteCompaniesStartingWithA();

        List<Company> remainingCompanies = companyRepository.findAll();
        List<String> remainingCompanyNames = remainingCompanies.stream()
                .map(Company::getName)
                .collect(Collectors.toList());

        assertThat(remainingCompanyNames)
                .doesNotContain("Apple", "Amazon", "Alphabet")
                .contains("Microsoft");
    }
}