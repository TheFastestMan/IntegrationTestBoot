package com.example.integrationtestboot.repository;

import com.example.integrationtestboot.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }



    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Transactional
    public boolean update(User user) {
        if (user != null) {
            sessionFactory.getCurrentSession().update(user);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delete(Long id) {
        User user = findUserById(id);
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
            return true;
        }
        return false;
    }


}

