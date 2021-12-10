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
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        checkDuplicateGiftCertificateByName(giftCertificate.getName());
        giftCertificateDao.create(giftCertificate);
        return processConnectCertificateWithTags(giftCertificateDto);
    }

    private long processConnectCertificateWithTags(GiftCertificateDto giftCertificateDto) {
        long certificateId = giftCertificateDao.findByName(
                giftCertificateDto.getGiftCertificate().getName())
                .map(GiftCertificate::getId).orElse(-1L);
        if (!giftCertificateDto.getTags().isEmpty()) {
            validateTags(giftCertificateDto.getTags());
            connectCertificateWithTags(certificateId, giftCertificateDto.getTags());
        }
        return certificateId;
    }

    private void connectCertificateWithTags(long certificateId, Set<Tag> tags) {
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
    public GiftCertificateDto updateById(long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDto.getGiftCertificate();
        if (giftCertificate != null) {
            checkExistGiftCertificateById(id);
            giftCertificateDao.updateById(id, findUpdateInfo(giftCertificate));
        }
        Set<Tag> tags = giftCertificateDto.getTags();
        if (tags != null) {
            updateCertificateTags(tags, id);
        }
        return buildGiftCertificateDto(giftCertificateDao.findById(id).get());
    }

    private GiftCertificateDto buildGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .giftCertificate(giftCertificate)
                .tags(new HashSet<>())
                .build();
        HashSet<Optional<Tag>> optionalTags = new HashSet<>();
        List<Long> tagIds = giftCertificateHasTagDao.findTagIdsByCertificateId(giftCertificate.getId());
        tagIds.forEach(id -> optionalTags.add(tagDao.findById(id)));
        optionalTags.stream()
                .filter(Optional::isPresent)
                .forEach(tag -> giftCertificateDto.getTags().add(tag.get()));
        return giftCertificateDto;
    }

    private void updateCertificateTags(Set<Tag> tags, long giftCertificateId) {
        for (Tag tag : tags) {
            Optional<Tag> tagOptional = tagDao.findByName(tag.getName());
            Tag tagSureExist = tagOptional.orElseGet(() -> createCertificateTag(tag));
            long tagId = tagSureExist.getId();
            if (!giftCertificateHasTagDao.findTagIdsByCertificateId(giftCertificateId).contains(tagId)) {
                GiftCertificateHasTag giftCertificateHasTag = GiftCertificateHasTag.builder()
                        .giftCertificateId(giftCertificateId)
                        .tagId(tagId)
                        .build();
                giftCertificateHasTagDao.create(giftCertificateHasTag);
            }
        }
    }

    private Map<String, Object> findUpdateInfo(GiftCertificate giftCertificate) {
        Map<String, Object> updateInfo = new HashMap<>();
        ((GiftCertificateValidator)giftCertificateValidator)
                .isValidateCarefullyGiftCertificateValues(giftCertificate);
        updateInfo.put(ColumnName.NAME, giftCertificate.getName());
        updateInfo.put(ColumnName.DESCRIPTION, giftCertificate.getDescription());
        updateInfo.put(ColumnName.PRICE, giftCertificate.getPrice());
        updateInfo.put(ColumnName.DURATION, giftCertificate.getDuration());
        return updateInfo;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<GiftCertificate> certificateOptional = giftCertificateDao.findById(id);
        if (!certificateOptional.isPresent()) {
            throw new NotFoundEntityException("certificate.not.found");
        }
        giftCertificateDao.deleteById(id);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findAllWithTags(String tagName,
                                                    String partInfo,
                                                    List<String> sortColumns,
                                                    List<String> orderTypes) {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        List<GiftCertificate> giftCertificates;

        if (sortColumns != null && !sortColumns.isEmpty()) {
            giftCertificates = sortGiftCertificates(tagName, partInfo, sortColumns, orderTypes);
        } else if (isValidRequestAttributeLine(tagName) || isValidRequestAttributeLine(partInfo)) {
            giftCertificates = findCertificatesWithFiltering(tagName, partInfo);
        } else {
            giftCertificates = giftCertificateDao.findAll();
        }

        giftCertificates.forEach(g -> {
            giftCertificateDtoList.add(buildGiftCertificateDto(g));
        });

        return giftCertificateDtoList;
    }

    private List<GiftCertificate> sortGiftCertificates(String tagName,
                                                       String partInfo,
                                                       List<String> sortColumns,
                                                       List<String> orderTypes) {
        List<GiftCertificate> giftCertificates;
        SortParamsContext sortParamsContext = new SortParamsContext(sortColumns, orderTypes);
        validateSortParamsContext(sortParamsContext);

        if (isValidRequestAttributeLine(tagName) || isValidRequestAttributeLine(partInfo)) {
            giftCertificates = findGiftCertificatesWithSortingAndFiltering(
                    tagName, partInfo, sortParamsContext);
        } else {
            giftCertificates = giftCertificateDao.findAllWithSorting(sortParamsContext);
        }

        return giftCertificates;
    }

    private boolean isValidRequestAttributeLine(String line) {
        if (line == null) {
            return false;
        }
        line = line.trim();
        return !line.isEmpty();
    }

    private List<GiftCertificate> findCertificatesWithFiltering(String tagName, String partInfo) {
        List<Long> certificateIdsByTagName = null;
        if (tagName != null) {
            certificateIdsByTagName = findCertificateIdsByTagName(tagName);
        }
        return giftCertificateDao.findAllWithFiltering(certificateIdsByTagName, partInfo);
    }

    private List<GiftCertificate> findGiftCertificatesWithSortingAndFiltering(String tagName, String partInfo,
                                                                              SortParamsContext sortParamsContext) {
        List<Long> certificateIdsByTagName = null;
        if (tagName != null) {
            certificateIdsByTagName = findCertificateIdsByTagName(tagName);
        }
        return giftCertificateDao.findAllWithSortingFiltering(sortParamsContext,
                certificateIdsByTagName, partInfo);
    }

    private List<Long> findCertificateIdsByTagName(String tagName) {
        Optional<Tag> tag = tagDao.findByName(tagName);
        if (!tag.isPresent()) {
            throw new NotFoundEntityException("tag.not.found");
        }
        long tagId = tag.get().getId();
        return giftCertificateHasTagDao.findCertificateIdsByTagId(tagId);
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

    private void checkDuplicateGiftCertificateByName(String name) {
        if (giftCertificateDao.findByName(name).isPresent()) {
            throw new DuplicateEntityException("certificate.already.exist");
        }
    }

    private void checkExistGiftCertificateById(long id) {
        if (giftCertificateDao.findById(id).isPresent()) {
            throw new NotFoundEntityException("certificate.not.found");
        }
    }

    private void validateSortParamsContext(SortParamsContext sortParamsContext) {
        if (!sortParamsContextValidator.isValid(sortParamsContext)) {
            throw new InvalidEntityException("sort.params.invalid");
        }
    }
}
