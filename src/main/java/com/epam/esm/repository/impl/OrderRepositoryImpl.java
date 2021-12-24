package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.util.ColumnName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager, Class<Order> entityClass) {
        super(entityManager, entityClass);
    }

    @Override
    public List<Order> findAllByUserId(long userId, int page, int size) {
        CriteriaQuery<Order> query = buildGetAllQuery(userId);
        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Order> findAllByUserId(long userId) {
        CriteriaQuery<Order> query = buildGetAllQuery(userId);

        return entityManager.createQuery(query)
                .getResultList();
    }

    private CriteriaQuery<Order> buildGetAllQuery(long userId) {
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        Join<User, Order> userOrderJoin = root.join("user");
        Predicate joinPredicate = builder.equal(userOrderJoin.get(ColumnName.ID), userId);
        query.where(joinPredicate);

        return query;
    }
}
