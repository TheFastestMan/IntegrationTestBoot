package com.example.integrationtestboot.service;

import com.example.integrationtestboot.dto.CompanyDTO;
import com.example.integrationtestboot.entity.Company;
import com.example.integrationtestboot.listener.AccessType;
import com.example.integrationtestboot.listener.EventEntity;
import com.example.integrationtestboot.mapper.CompanyMapper;
import com.example.integrationtestboot.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private final CompanyRepository companyRepository;
    @Autowired
    private final CompanyMapper companyMapper;
    @Autowired
    private final ApplicationEventPublisher applicationEventPublisher;

    public CompanyService(CompanyRepository companyRepository,
                          CompanyMapper companyMapper,
                          ApplicationEventPublisher applicationEventPublisher) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void registerCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.toCompany(companyDTO);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.CREATE, "Before"));
        companyRepository.save(company);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.CREATE, "After"));
    }

    @Transactional(readOnly = true)
    public Optional<CompanyDTO> findCompanyById(Long id) {
        Company company = companyRepository.findCompanyById(id);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.READ, "Before"));
        CompanyDTO companyDTO = companyMapper.toCompanyDTO(company);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.READ, "After"));
        return Optional.ofNullable(companyDTO);
    }

    public String updateCompany(CompanyDTO companyDTO) {
        Company company = companyMapper.toCompany(companyDTO);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.UPDATE, "Before"));
        boolean companyVar = companyRepository.update(company);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.UPDATE, "After"));
        if (companyVar == true) {
            return "Company has updated";
        }
        return "Company has NOT updated!";
    }

    public String deleteCompany(Long id) {
        Company company = companyRepository.findCompanyById(id);
        if (company != null) {
            applicationEventPublisher.publishEvent(new EventEntity(company,
                    AccessType.UPDATE, "Before"));
            boolean companyVar = companyRepository.delete(id);
            applicationEventPublisher.publishEvent(new EventEntity(company, AccessType.DELETE, "After"));
            return companyVar ? "Company has deleted" : "Company has NOT deleted!";
        }
        return "Company has NOT deleted!";
    }

}
