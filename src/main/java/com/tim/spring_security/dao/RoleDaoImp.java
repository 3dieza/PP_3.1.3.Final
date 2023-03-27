package com.tim.spring_security.dao;

import com.tim.spring_security.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Set<Role> findRoles(List<Long> roles) {
        TypedQuery<Role> q = entityManager.createQuery("select distinct r from Role r join fetch r.users u where r.id in :role", Role.class);
        q.setParameter("role", roles);
        return new HashSet<>(q.getResultList());
    }

    public List<Role> getAllRoles() {
        return entityManager.createQuery("select distinct r from Role r left join fetch r.users", Role.class).getResultList();
    }

}