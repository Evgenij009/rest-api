package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    long create(GiftCertificateDto giftCertificateDto);
    List<GiftCertificate> findAll();
    GiftCertificate findById(long id);
    GiftCertificateDto updateById(long id, GiftCertificateDto giftCertificateDto);
    void deleteById(long id);
    List<GiftCertificateDto> findAllWithTags(String tagName, String partInfo, List<String> sortColumns, List<String> orderTypes);
}
