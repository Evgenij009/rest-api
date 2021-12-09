package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.util.SortParamsContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO interface for Gift certificate.
 */
public interface GiftCertificateDao {
    /**
     * Create new Certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the long id new certificate
     */
    long create(GiftCertificate giftCertificate);

    /**
     * Find all certificates.
     *
     * @return the list of certificates
     */
    List<GiftCertificate> findAll();

    /**
     * Gets all Certificates with sorting.
     *
     * @param sortParamsContext sort parameters
     * @return List of sorted Certificates
     */
    List<GiftCertificate> findAllWithSorting(SortParamsContext sortParamsContext);

    /**
     * Gets all Certificates with filtering.
     *
     * @param ids      ids for filtering Certificates
     * @param partInfo part info of name/description of Certificate to filter
     * @return List of filtered Certificates
     */
    List<GiftCertificate> findAllWithFiltering(List<Long> ids, String partInfo);

    /**
     * Gets all Certificates with sorting and filtering.
     *
     * @param sortParamsContext sort parameters
     * @param ids      ids for filtering Certificates
     * @param partInfo part info of name/description of Certificate to filter
     * @return List of filtered and sorted Certificates
     */
    List<GiftCertificate> findAllWithSortingFiltering(SortParamsContext sortParamsContext,
                                                      List<Long> ids, String partInfo);

    /**
     * Updates Certificate by id by map of update info
     *
     * @param id                 Certificate id to search
     * @param giftCertificateInfo Update information with Certificate fields and values
     */
    void updateById(long id, Map<String, Object> giftCertificateInfo);

    /**
     * Finds Certificate by id.
     *
     * @param id Certificate id to search
     * @return Optional Certificate - Certificate if founded or Empty if not
     */
    Optional<GiftCertificate> findById(long id);

    /**
     * Finds Certificate by name.
     *
     * @param name Certificate name to search
     * @return Optional Certificate - Certificate if founded or Empty if not
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Deletes Certificate by id.
     *
     * @param id Certificate id to search
     */
    void deleteById(long id);
}
