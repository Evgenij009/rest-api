package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificateHasTag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateHasTagDao {
    void create(GiftCertificateHasTag giftCertificateHasTag);
    List<GiftCertificateHasTag> findAll();
    Optional<GiftCertificateHasTag> findById(long id);
    Optional<GiftCertificateHasTag> findByTagId(long id);
    Optional<GiftCertificateHasTag> findByGiftCertificateId(long id);
}
