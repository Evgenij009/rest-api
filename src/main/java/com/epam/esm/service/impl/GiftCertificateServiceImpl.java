package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateHasTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateHasTag;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateHasTagDao giftCertificateHasTagDao;
    private final Validator<GiftCertificate> giftCertificateValidator;
    private final Validator<Tag> tagValidator;
    private final Validator<SortParamsContext> sortParamsContextValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateHasTagDao giftCertificateHasTagDao,
                                      Validator<GiftCertificate> giftCertificateValidator,
                                      Validator<Tag> tagValidator,
                                      Validator<SortParamsContext> sortParamsContextValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateHasTagDao = giftCertificateHasTagDao;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagValidator = tagValidator;
        this.sortParamsContextValidator = sortParamsContextValidator;
    }

    @Override
    @Transactional
    public long create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
        validateGiftCertificate(giftCertificate);
        validateTags(giftCertificateDto.getCertificateTags());
        checkGiftCertificateForExist(giftCertificate.getName());
        giftCertificateDao.create(giftCertificate);
        long certificateId = giftCertificateDao.findByName(giftCertificate.getName())
                .map(GiftCertificate::getId).orElse(-1L);
        tieCertificateWithTags(certificateId, giftCertificateDto.getCertificateTags());
        return certificateId;
    }

    private void tieCertificateWithTags(long certificateId, Set<Tag> tags) {
        for (Tag tag : tags) {
            Optional<Tag> tagOptional = tagDao.findByName(tag.getName());
            Tag tagSureExist = tagOptional.orElseGet(() -> createCertificateTag(tag));
            long tagId = tagSureExist.getId();
            GiftCertificateHasTag giftCertificateHasTag = GiftCertificateHasTag.builder()
                            .tagId(tagId)
                            .giftCertificateId(certificateId)
                            .build();
            giftCertificateHasTagDao.create(giftCertificateHasTag);
        }
    }

    private Tag createCertificateTag(Tag tag) {
        tagDao.create(tag);
        return tagDao.findByName(tag.getName()).get();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        if (!giftCertificateOptional.isPresent()) {
            throw new NotFoundEntityException("certificate.not.found");
        }
        return giftCertificateOptional.get();
    }

    @Override
    @Transactional
    public GiftCertificateDao updateById(long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
//        if (giftCertificate != null) {
//            if (!giftCertificateDao.findById(id).isPresent()) {
//                throw new NotFoundEntityException("certificate.not.found");
//            }
//            giftCertificateDao.updateById(id, findUpdateInfo(giftCertificate));
//        }
//        Set<Tag> tags = giftCertificateDto.getCertificateTags();
//        if (tags != null) {
//            updateCertificateTags(tags, id);
//        }
//        return buildGiftCertificateDto(giftCertificateDao.findById(id).get());
        return null;
    }

    @Override
    public void deleteById(long id) {
        Optional<GiftCertificate> certificateOptional = giftCertificateDao.findById(id);
        if (!certificateOptional.isPresent()) {
            throw new NotFoundEntityException("certificate.not.found");
        }
        giftCertificateDao.deleteById(id);
    }

    private void validateTags(Set<Tag> tags) {
        for (Tag tag : tags) {
            if (!tagValidator.isValid(tag)) {
                throw new InvalidEntityException("tag.invalid");
            }
        }
    }

    private void validateGiftCertificate(GiftCertificate giftCertificate) {
        if (!giftCertificateValidator.isValid(giftCertificate)) {
            throw new InvalidEntityException("certificate.invalid");
        }
    }

    private void checkGiftCertificateForExist(String name) {
        if (giftCertificateDao.findByName(name).isPresent()) {
            throw new DuplicateEntityException("certificate.already.exist");
        }
    }
}
