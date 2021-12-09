package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificateHasTag;

import java.util.List;
import java.util.Optional;

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
     * Find gift certificate has tag by id.
     *
     * @param id the id certificate has reference on tag
     * @return the optional if situation empty
     */
    Optional<GiftCertificateHasTag> findById(long id);

    /**
     * Find certificate has reference on tag by tag id.
     *
     * @param id the id certificate has reference on tag
     * @return the optional certificate has reference on tag
     */
    Optional<GiftCertificateHasTag> findByTagId(long id);

    /**
     * Find by gift certificate id optional.
     *
     * @param id the id certificate has reference on tag
     * @return the optional certificate has reference on tag
     */
    Optional<GiftCertificateHasTag> findByGiftCertificateId(long id);

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
