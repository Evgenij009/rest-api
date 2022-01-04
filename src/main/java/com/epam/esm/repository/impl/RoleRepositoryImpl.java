package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.Role;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.util.ColumnName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return findByField(ColumnName.NAME, name);
    }
}
