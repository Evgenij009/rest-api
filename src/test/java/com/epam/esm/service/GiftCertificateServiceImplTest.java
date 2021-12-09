package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateHasTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.SortParamsContextValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GiftCertificateServiceImplTest {
    private static final long ID = 1;
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(ID, "name",
            "description", BigDecimal.TEN, 5, new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis()));
    private static final GiftCertificateDto GIFT_CERTIFICATE_DTO
            = new GiftCertificateDto(GIFT_CERTIFICATE, new HashSet<>());
    private GiftCertificateDao certificateDao;
    private Validator<GiftCertificate> certificateValidator;
    private TagDao tagDao;
    private Validator<Tag> tagValidator;
    private Validator<SortParamsContext> sortParamsContextValidator;
    private GiftCertificateHasTagDao giftCertificateHasTagDao;
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    public void initMethod() {
        certificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        certificateValidator = Mockito.mock(GiftCertificateValidator.class);
        tagDao = Mockito.mock(TagDaoImpl.class);
        tagValidator = Mockito.mock(TagValidator.class);
        giftCertificateHasTagDao = Mockito.mock(GiftCertificateHasTagDao.class);
        sortParamsContextValidator = Mockito.mock(SortParamsContextValidator.class);
        giftCertificateService = new GiftCertificateServiceImpl(
                certificateDao,
                tagDao,
                giftCertificateHasTagDao,
                certificateValidator,
                tagValidator,
                sortParamsContextValidator
        );
    }

    @Test
    public void testCreate_ShouldCreateWhenNotExistAndValid() {
        when(certificateValidator.isValid(any())).thenReturn(true);
        when(tagValidator.isValid(any())).thenReturn(true);
        when(certificateDao.findByName(anyString())).thenReturn(Optional.empty());
        giftCertificateService.create(GIFT_CERTIFICATE_DTO);
        verify(certificateDao).create(GIFT_CERTIFICATE);
    }

    @Test
    public void testFindAll_ShouldGetAll() {
        giftCertificateService.findAll();
        verify(certificateDao).findAll();
    }

    @Test
    public void testFindByIdShouldThrowsNotFoundEntityExceptionWhenNotFound() {
        when(certificateDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> giftCertificateService.findById(ID));
    }

    @Test
    public void testFindAllWithTagsShouldFindWithSortingAndFilteringWhenExist() {
        when(sortParamsContextValidator.isValid(any())).thenReturn(true);
        giftCertificateService.findAllWithTags(null, "p",
                Collections.singletonList("name"), null);
        verify(certificateDao).findAllWithSortingFiltering(any(), any(), anyString());
    }

    @Test
    public void testFindAllWithTags_ShouldFindAllWhenFilteringAndSortingNotExist() {
        giftCertificateService.findAllWithTags(null, null, null, null);
        verify(certificateDao).findAll();
    }

    @Test
    public void testFindAllWithTags_ShouldGetWithSFilteringWhenFilteringExist() {
        giftCertificateService.findAllWithTags(null, "z", null, null);
        verify(certificateDao).findAllWithFiltering(any(), anyString());
    }

    @Test
    public void testFindAllWithTags_ShouldGetWithSortingWhenSortingExist() {
        when(sortParamsContextValidator.isValid(any())).thenReturn(true);
        giftCertificateService.findAllWithTags(null, null,
                Collections.singletonList("name"), null);
        verify(certificateDao).findAllWithSorting(any());
    }

    @Test
    public void testUpdateByIds_ShouldUpdateWhenFound() {
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(GIFT_CERTIFICATE));
        when(((GiftCertificateValidator)certificateValidator).isNameValid(anyString())).thenReturn(true);
        when(((GiftCertificateValidator)certificateValidator).isDescriptionValid(anyString())).thenReturn(true);
        when(((GiftCertificateValidator)certificateValidator).isDurationValid(anyInt())).thenReturn(true);
        when(((GiftCertificateValidator)certificateValidator).isPriceValid(any())).thenReturn(true);
        giftCertificateService.updateById(ID, GIFT_CERTIFICATE_DTO);
        verify(certificateDao).updateById(anyLong(), any());
    }
}
