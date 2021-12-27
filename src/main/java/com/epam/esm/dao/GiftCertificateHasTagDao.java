package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificateHasTag;

import java.util.List;

/**
 * DAO interface Gift certificate has tag.
 */
public interface GiftCertificateHasTagDao {
    /**
     * Create new Certificate has reference on tag.
     *
     * @param giftCertificateHasTag the gift certificate has tag
     */
    void create(GiftCertificateHasTag giftCertificateHasTag);

    /**
     * Find all list certificate has reference on tag.
     *
     * @return the list of gift certificate has tag
     */
    List<GiftCertificateHasTag> findAll();

    /**
     * Find all certificate ids by tag id.
     *
     * @param tagId the tag id
     * @return the list od certificate ids
     */
    List<Long> findCertificateIdsByTagId(long tagId);

    /**
     * Find all tag ids by certificate id.
     *
     * @param certificateId the certificate id
     * @return the list of Tag ids.
     */
    List<Long> findTagIdsByCertificateId(long certificateId);
}
