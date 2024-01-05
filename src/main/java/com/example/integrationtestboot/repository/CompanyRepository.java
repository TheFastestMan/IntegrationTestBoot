package com.example.integrationtestboot.repository;

import com.example.integrationtestboot.entity.Company;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CompanyRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }

    @Transactional(readOnly = true)
    public Company findCompanyById(Long id) {
        return sessionFactory.getCurrentSession().get(Company.class, id);
    }

    @Transactional
    public boolean update(Company company) {
        if (company != null) {
            sessionFactory.getCurrentSession().update(company);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delete(Long id) {
        Company company = findCompanyById(id);
        if (company != null) {
            sessionFactory.getCurrentSession().delete(company);
            return true;
        }
        return false;
    }
}
