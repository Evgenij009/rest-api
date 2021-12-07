package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.util.SortParamsContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {
    long create(GiftCertificate giftCertificate);
    List<GiftCertificate> findAll();
    List<GiftCertificate> findAllWithSorting(SortParamsContext sortParamsContext);
    List<GiftCertificate> findAllWithFiltering(List<Long> ids, String partInfo);
    List<GiftCertificate> findAllWithSortingFiltering(SortParamsContext sortParamsContext,
                                                      List<Long> ids, String partInfo);
    List<Long> findTagIdsByCertificateId(long certificateId);
    List<Long> findCertificateIdsByTagId(long tagId);
    void updateById(long id, Map<String, Object> giftCertificateInfo);
    Optional<GiftCertificate> findById(long id);
    Optional<GiftCertificate> findByName(String name);
    void deleteById(long id);
}
