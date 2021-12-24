package com.epam.esm.repository;

import com.epam.esm.model.entity.BaseEntity;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.UtilBuilderQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends BaseEntity> implements CrudRepository<T> {

    @PersistenceContext
    protected final EntityManager entityManager;

    protected final CriteriaBuilder builder;
    protected final UtilBuilderQuery utilBuilderQuery;
    protected final Class<T> entityClass;

    public AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.utilBuilderQuery = new UtilBuilderQuery(this.builder);
        this.entityClass = entityClass;
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<T> findAll(int page, int size) {
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);

        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<T> findById(long id) {
        return findByField(ColumnName.ID, id);
    }

    @Override
    public Optional<T> findByField(String columnName, Object value) {
        CriteriaQuery<T> entityQuery = builder.createQuery(entityClass);
        Root<T> root = entityQuery.from(entityClass);
        entityQuery.select(root);

        Predicate fieldPredicate = builder.equal(root.get(columnName), value);
        entityQuery.where(fieldPredicate);

        TypedQuery<T> typedQuery = entityManager.createQuery(entityQuery);
        return utilBuilderQuery.getOptionalQueryResult(typedQuery);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(long id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
