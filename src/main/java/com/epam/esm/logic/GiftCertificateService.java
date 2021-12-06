package com.epam.esm.logic;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    long create(GiftCertificateDto giftCertificateDto);
    List<GiftCertificate> findAll();
    GiftCertificate findById(long id);
    GiftCertificateDao updateById(long id, GiftCertificate giftCertificate);
    boolean deleteById(long id);
}
