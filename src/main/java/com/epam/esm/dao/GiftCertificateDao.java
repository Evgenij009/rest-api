package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    long create(GiftCertificate giftCertificate);
    List<GiftCertificate> findAll();
    Optional<GiftCertificate> findById(long id);
    Optional<GiftCertificate> findByName(String name);
}
