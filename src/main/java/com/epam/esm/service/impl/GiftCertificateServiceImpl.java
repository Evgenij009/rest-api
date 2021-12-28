package com.epam.esm.service.impl;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.UpdateGiftCertificateMapper;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.UpdateGiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.validator.SortParamsContextValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String CERTIFICATE_NOT_FOUND = "certificate.not.found";
    private static final String CERTIFICATE_ALREADY_EXIST = "certificate.already.exist";
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateMapper giftCertificateMapper,
                                      UpdateGiftCertificateMapper updateGiftCertificateMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateMapper = giftCertificateMapper;
        this.updateGiftCertificateMapper = updateGiftCertificateMapper;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.mapToEntity(giftCertificateDto);
        checkDuplicateGiftCertificate(giftCertificate);
        List<Tag> tagsToAdd = new ArrayList<>();
        if (giftCertificate.getTagList() != null) {
            for (Tag tag : giftCertificate.getTagList()) {
                Optional<Tag> tagOptional = tagRepository.findByName(tag.getName());
                if (tagOptional.isPresent()) {
                    tagsToAdd.add(tagOptional.get());
                } else {
                    tagsToAdd.add(tag);
                }
            }
        }
        giftCertificate.setTagList(tagsToAdd);
        return giftCertificateMapper.mapToDto(giftCertificateRepository.create(giftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(long id) {
        GiftCertificate giftCertificate = checkExistGiftCertificateById(id);
        return giftCertificateMapper.mapToDto(giftCertificate);
    }

    @Override
    @Transactional(rollbackFor = NotFoundEntityException.class)
    public GiftCertificateDto updateById(long id, UpdateGiftCertificateDto updateGiftCertificateDto) {
        GiftCertificate giftCertificate = checkExistGiftCertificateById(id);
        GiftCertificate updateGiftCertificate = updateGiftCertificateMapper.mapToEntity(updateGiftCertificateDto);
        updateFields(giftCertificate, updateGiftCertificate);
        if (giftCertificate.getTagList() != null) {
            List<Tag> tags = giftCertificate.getTagList();
            giftCertificate.setTagList(addTags(tags));
        }
        return giftCertificateMapper.mapToDto(giftCertificateRepository.update(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        checkExistGiftCertificateById(id);
        giftCertificateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findBySearchParams(List<String> tagNames,
                                                       String partInfo,
                                                       List<String> sortColumns,
                                                       List<String> orderTypes,
                                                       int page, int size) {
        SortParamsContext sortParamsContext = null;
        if (sortColumns != null) {
            sortParamsContext = new SortParamsContext(sortColumns, orderTypes);
            SortParamsContextValidator.validateParams(sortParamsContext);
        }
        return giftCertificateMapper.mapListToDto(
                giftCertificateRepository.findAllWithSortingFiltering(
                        sortParamsContext, tagNames, partInfo, page, size
                )
        );
    }

    @Override
    @Transactional(rollbackFor = NotFoundEntityException.class)
    public GiftCertificateDto replaceById(long id, GiftCertificateDto giftCertificateDto)
            throws NotFoundEntityException {
        GiftCertificate giftCertificate = checkExistGiftCertificateById(id);
        GiftCertificate replaceGiftCertificate = giftCertificateMapper.mapToEntity(giftCertificateDto);
        updateFields(giftCertificate, replaceGiftCertificate);
        if (giftCertificate.getTagList() != null) {
            List<Tag> tags = giftCertificate.getTagList();
            giftCertificate.setTagList(addTags(tags));
        }
        return giftCertificateMapper.mapToDto(giftCertificateRepository.update(giftCertificate));
    }

    private void updateFields(GiftCertificate giftCertificate, GiftCertificate updateGiftCertificate) {
        String name = updateGiftCertificate.getName();
        if (name != null && !giftCertificate.getName().equals(name)) {
            giftCertificate.setName(name);
        }
        String description = updateGiftCertificate.getDescription();
        if (description != null && !giftCertificate.getDescription().equals(description)) {
            giftCertificate.setDescription(description);
        }
        BigDecimal price = updateGiftCertificate.getPrice();
        if (price != null && giftCertificate.getPrice().compareTo(price) != 0) {
            giftCertificate.setPrice(price);
        }
        int duration = updateGiftCertificate.getDuration();
        if (duration != 0 && giftCertificate.getDuration() != duration) {
            giftCertificate.setDuration(duration);
        }
    }

    private List<Tag> addTags(List<Tag> tags) {
        List<Tag> addedTags = new ArrayList<>();
        for (Tag tag : tags) {
            Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());
            Tag savedTag = optionalTag.orElseGet(() -> tagRepository.create(tag));
            addedTags.add(savedTag);
        }
        return addedTags;
    }

    private GiftCertificate checkExistGiftCertificateById(long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(CERTIFICATE_NOT_FOUND));
    }

    private void checkDuplicateGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificateRepository.findByName(giftCertificate.getName()).isPresent()) {
            throw new DuplicateEntityException(CERTIFICATE_ALREADY_EXIST);
        }
    }
}
