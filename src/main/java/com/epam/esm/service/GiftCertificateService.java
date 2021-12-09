package com.epam.esm.service;

import com.epam.esm.exception.InvalidEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;

/**
 * Business logic interface for Certificates.
 */
public interface GiftCertificateService {
    /**
     * Creates new Certificate with optional tags.
     *
     * @param giftCertificateDto CertificateDto to create Certificate/Tags
     * @return id location of new Certificate
     * @throws InvalidEntityException when Certificate or Tag data is invalid
     */
    long create(GiftCertificateDto giftCertificateDto);

    /**
     * Find all Certificates.
     *
     * @return List of all Certificates
     */
    List<GiftCertificate> findAll();

    /**
     * Find Certificate by id.
     *
     * @param id Certificate id to search
     * @return founded Certificate
     * @throws NotFoundEntityException when Certificate is not found
     */
    GiftCertificate findById(long id);

    /**
     * Updates Certificate by id
     * with updating only fields that are passed
     *
     * @param id              Certificate id to search
     * @param giftCertificateDto update information with Certificate fields or Tags
     * @return updated Certificate with Tags
     * @throws NotFoundEntityException when Certificate is not found
     * @throws InvalidEntityException when update info of Certificate fields is invalid
     */
    GiftCertificateDto updateById(long id, GiftCertificateDto giftCertificateDto);

    /**
     * Deletes Certificate by id.
     *
     * @param id Certificate id to search
     * @throws NotFoundEntityException when Certificate is not found
     */
    void deleteById(long id);

    /**
     * Find all Certificates with Tags and optional sorting/filtering
     *
     * @param tagName     Tag name to filter Certificates
     * @param partInfo    part info of name/desc to filter Certificates
     * @param sortColumns columns to sort of Certificates
     * @param orderTypes  sort order types
     * @return List of sorted/filtered Certificates with Tags
     * @throws NotFoundEntityException when Tag is not found
     * @throws InvalidEntityException when sort parameters are invalid
     */
    List<GiftCertificateDto> findAllWithTags(String tagName, String partInfo, List<String> sortColumns, List<String> orderTypes);
}
