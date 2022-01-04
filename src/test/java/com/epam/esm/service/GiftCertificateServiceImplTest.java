package com.epam.esm.service;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.GiftCertificateMapperImpl;
import com.epam.esm.mapper.UpdateGiftCertificateMapperImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.validator.SortParamsContextValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        GiftCertificateServiceImpl.class,
        GiftCertificateMapperImpl.class,
        UpdateGiftCertificateMapperImpl.class})
public class GiftCertificateServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final ZonedDateTime UPDATE_TIME = ZonedDateTime.now();
    private static final ZonedDateTime CREATE_TIME = ZonedDateTime.now();
    private static final int DURATION = 5;
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate(
            ID, NAME, DESCRIPTION, PRICE, DURATION, CREATE_TIME, UPDATE_TIME, null
    );
    private static final Tag TAG = new Tag(ID, "new");
    private static final GiftCertificate GIFT_CERTIFICATE_WITH_TAGS = new GiftCertificate(
            ID, NAME, DESCRIPTION, PRICE, DURATION, CREATE_TIME, UPDATE_TIME, null
    );

    static {
        List<Tag> tagList = new ArrayList<>(Arrays.asList(TAG));
        GIFT_CERTIFICATE_WITH_TAGS.setTagList(tagList);
    }

    private static final String PART_INFO = "z";
    private static final List<String> SORTING_COLUMN = Collections.singletonList("name");
    private static final SortParamsContext SORT_PARAMS = new SortParamsContext(SORTING_COLUMN, null);
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    @MockBean
    private GiftCertificateRepositoryImpl certificateRepository;
    @MockBean
    private TagRepositoryImpl tagRepository;
    @MockBean
    private SortParamsContextValidator sortParamsContextValidator;
    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;
    @Autowired
    private GiftCertificateMapperImpl giftCertificateMapper;
    @Autowired
    private UpdateGiftCertificateMapperImpl updateGiftCertificateMapper;

    @Test()
    public void testGetAllShouldThrowsValidationExceptionWhenParamsInvalid() {
        Assertions.assertThrows(ValidationException.class, () -> {
            giftCertificateService.findBySearchParams(null, null, null, null, -3, -2);
        });
    }

    @Test()
    public void getAllWithTagsShouldThrowsValidationExceptionWhenParamsInvalid() {
        Assertions.assertThrows(ValidationException.class, () -> {
            giftCertificateService.findBySearchParams(null, null,
                    null, null, -3, -2);
        });
    }

    @Test
    public void testGetByIdShouldGetWhenFound() {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(GIFT_CERTIFICATE));
        giftCertificateService.findById(ID);
        verify(certificateRepository).findById(ID);
    }

    @Test()
    public void testGetByIdShouldThrowsNoFoundEntityExceptionWhenNotFound() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> {
            when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());
            giftCertificateService.findById(ID);
        });
    }

    @Test
    public void testUpdateByIdShouldUpdateWhenFound() {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(GIFT_CERTIFICATE));
        giftCertificateService.updateById(ID, updateGiftCertificateMapper.mapToDto(GIFT_CERTIFICATE));
        verify(certificateRepository).update(GIFT_CERTIFICATE);
    }

    @Test
    public void testUpdateByIdShouldCreateTagWhenNewTagPassed() {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(GIFT_CERTIFICATE_WITH_TAGS));
        when(tagRepository.findByName(any())).thenReturn(Optional.empty());
        giftCertificateService.updateById(ID, updateGiftCertificateMapper.mapToDto(GIFT_CERTIFICATE_WITH_TAGS));
        verify(tagRepository).create(TAG);
    }

    @Test
    public void testUpdateByIdShouldThrowsNoSuchEntityExceptionWhenNotFound() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> {
            when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());
            giftCertificateService.updateById(ID, updateGiftCertificateMapper.mapToDto(GIFT_CERTIFICATE));
        });
    }

    @Test
    public void testDeleteByIdShouldDeleteWhenFound() {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(GIFT_CERTIFICATE));
        giftCertificateService.deleteById(ID);
        verify(certificateRepository).deleteById(ID);
    }

    @Test
    public void testDeleteByIdShouldThrowsNoSuchEntityExceptionWhenNotFound() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> {
            when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());
            giftCertificateService.deleteById(ID);
        });
    }
}