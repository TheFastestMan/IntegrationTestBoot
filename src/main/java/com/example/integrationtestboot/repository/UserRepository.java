package com.example.integrationtestboot.repository;

import com.example.integrationtestboot.entity.Role;
import com.example.integrationtestboot.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRoleAndBirthDateBetween(Role role, LocalDate start, LocalDate end);

    List<User> findFirst4ByOrderByIdDesc();

    Page<User> findByRole(Role role, Pageable pageable);
}

