package com.tim.spring_security.service;

import com.tim.spring_security.dao.RoleDao;
import com.tim.spring_security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> findRoles(List<Long> roles) {
        return roleDao.findRoles(roles);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }
}