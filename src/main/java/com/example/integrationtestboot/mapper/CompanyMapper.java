package com.example.integrationtestboot.mapper;

import com.example.integrationtestboot.dto.CompanyDTO;
import com.example.integrationtestboot.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    @Autowired
    private UserMapper userMapper;

    public CompanyDTO toCompanyDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setUser(userMapper.userToUserDTO(company.getUser()));
        dto.setName(company.getName());
        return dto;
    }

    public Company toCompany(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setUser(userMapper.userDTOToUser(dto.getUser()));
        company.setName(dto.getName());
        return company;
    }
}
