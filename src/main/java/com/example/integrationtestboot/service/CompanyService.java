package com.example.integrationtestboot.service;

import com.example.integrationtestboot.dto.CompanyDTO;
import com.example.integrationtestboot.entity.Company;
import com.example.integrationtestboot.listener.AccessType;
import com.example.integrationtestboot.listener.EventEntity;
import com.example.integrationtestboot.mapper.CompanyMapper;
import com.example.integrationtestboot.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Optional<Company> company = companyRepository.findById(id);
        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.READ, "Before"));

        CompanyDTO companyDTO = companyMapper.toCompanyDTO(company.get());

        applicationEventPublisher.publishEvent(new EventEntity(company,
                AccessType.READ, "After"));
        return Optional.ofNullable(companyDTO);
    }

    @Transactional
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        // Find the existing company by ID
        Optional<Company> existingCompanyOpt = companyRepository.findById(id);

        // Check if company exists
        if (!existingCompanyOpt.isPresent()) {
            throw new EntityNotFoundException("Company with ID " + id + " not found");
        }

        // Map the updates from CompanyDTO to existing Company entity
        Company existingCompany = existingCompanyOpt.get();
        existingCompany.setName(companyDTO.getName());
        // ... (set other fields from DTO as needed)

        // Before update event
        applicationEventPublisher.publishEvent(new EventEntity(existingCompany,
                AccessType.UPDATE, "Before"));

        // Save the updated company entity
        Company updatedCompany = companyRepository.save(existingCompany);

        // After update event
        applicationEventPublisher.publishEvent(new EventEntity(updatedCompany,
                AccessType.UPDATE, "After"));

        // Convert updated company entity back to DTO to return
        return companyMapper.toCompanyDTO(updatedCompany);
    }
    @Transactional
    public void deleteCompaniesStartingWithA() {
        companyRepository.deleteByNameStartingWith("A");
    }
    @Transactional
    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }
    @Transactional
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

}
