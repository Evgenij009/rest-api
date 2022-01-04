package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortParamsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate>
        implements GiftCertificateRepository {
    private static final String TAG_LIST = "tagList";

    @Autowired
    public GiftCertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager, GiftCertificate.class);
    }

    @Override
    public List<GiftCertificate> findAllWithSortingFiltering(SortParamsContext sortParamsContext,
                                                             List<String> tagNames, String partInfo,
                                                             int page, int size) {
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (tagNames != null) {
            predicates.add(buildPredicateByTagName(root, tagNames));
        }
        if (partInfo != null) {
            predicates.add(buildPredicateByPartInfo(root, partInfo));
        }
        if (!predicates.isEmpty()) {
            query.where(utilBuilderQuery.buildAndPredicates(predicates));
            if (tagNames != null) {
                query.groupBy(root.get(ColumnName.ID));
                query.having(builder.greaterThanOrEqualTo(builder.count(root), (long) tagNames.size()));
            }
        }

        if (sortParamsContext != null) {
            List<Order> orderList = utilBuilderQuery.buildOrderList(root, sortParamsContext);
            if (!orderList.isEmpty()) {
                query.orderBy(orderList);
            }
        }

        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return findByField(ColumnName.NAME, name);
    }

    private Predicate buildPredicateByPartInfo(Root<GiftCertificate> root, String partInfo) {
        String regexValue = utilBuilderQuery.buildRegexValue(partInfo);
        Predicate predicateByNameInfo = builder.like(root.get(ColumnName.NAME), regexValue);
        Predicate predicateByDescriptionInfo = builder.like(root.get(ColumnName.DESCRIPTION), regexValue);

        return builder.or(predicateByNameInfo, predicateByDescriptionInfo);
    }

    private Predicate buildPredicateByTagName(Root<GiftCertificate> root, List<String> tagNames) {
        Join<GiftCertificate, Tag> tagJoin = root.join(TAG_LIST);

        return utilBuilderQuery.buildOrEqualPredicates(tagJoin, ColumnName.NAME, tagNames);
    }
}
