package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.Status;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    private static final String SPENT_MONEY = "spentMoney";

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public User findUserWithMaxSpentMoney() {
        CriteriaQuery<BigDecimal> bigDecimalCriteriaQuery = builder.createQuery(BigDecimal.class);
        Root<User> spentMoneyRoot = bigDecimalCriteriaQuery.from(entityClass);
        bigDecimalCriteriaQuery.select(builder.max(spentMoneyRoot.get(SPENT_MONEY)));
        BigDecimal maxSpentMoney = entityManager.createQuery(bigDecimalCriteriaQuery).getSingleResult();
        CriteriaQuery<User> query = builder.createQuery(entityClass);
        Root<User> root = query.from(entityClass);
        query.select(root);
        Predicate predicate = builder.equal(root.get(SPENT_MONEY), maxSpentMoney);
        query.where(predicate);
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void delete(User user) {
        user.setStatus(Status.DELETED);
        entityManager.merge(user);
    }
}
