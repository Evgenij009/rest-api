package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.util.SortParamsContext;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {
    List<GiftCertificate> findAllWithSortingFiltering(SortParamsContext sortParamsContext,
                                                      List<String> tagNames, String partInfo,
                                                      int page, int size);
    Optional<GiftCertificate> findByName(String name);
}
