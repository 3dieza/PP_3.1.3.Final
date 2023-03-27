package com.tim.spring_security.dao;

import com.tim.spring_security.model.Role;
import com.tim.spring_security.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findUserById(id));
    }

    @Override
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User change(User user, Set<Role> roles) {
        user.setRoles(roles);
        return entityManager.merge(user);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("select distinct u from User u join fetch u.roles", User.class).getResultList();
    }

    @Override
    public User findUserByEmail(String email) {
        Query query = entityManager.createQuery("select distinct u from User u join fetch u.roles where u.email=:email", User.class);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

}