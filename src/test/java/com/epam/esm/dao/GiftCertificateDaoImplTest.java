package com.epam.esm.dao;

import com.epam.esm.config.TestJdbcConfig;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.util.SortParamsContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Component
@ContextConfiguration(classes = {TestJdbcConfig.class})
@Transactional
public class GiftCertificateDaoImplTest {
    private static final SimpleDateFormat dateFormatISO8601 =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static GiftCertificate TEST_ENTITY_FOR_CREATE;
    private static final Tag FIRST_TAG = new Tag(1L, "tag 1");
    private static final Tag SECOND_TAG = new Tag(2L, "tag 2");
    private static final Tag THIRD_TAG = new Tag(3L, "tag 3");
    private static final List<Tag> firstCertificateTags = Arrays.asList(FIRST_TAG, THIRD_TAG);
    private static GiftCertificate FIRST_CERTIFICATE;
    private static GiftCertificate SECOND_CERTIFICATE;
    private static GiftCertificate THIRD_CERTIFICATE;
    private final GiftCertificateDaoImpl giftCertificateDao;
    private final GiftCertificateHasTagDao giftCertificateHasTagDao;

    static {
        try {
            TEST_ENTITY_FOR_CREATE = GiftCertificate.builder()
                    .id(4L)
                    .name("new certificate")
                    .description("new description")
                    .price(new BigDecimal("1.10"))
                    .createDate(dateFormatISO8601.parse("2021-12-08T10:39:09.000Z"))
                    .lastUpdateDate(dateFormatISO8601.parse("2021-12-09T00:39:09.000Z"))
                    .duration(1)
                    .build();
            FIRST_CERTIFICATE = GiftCertificate.builder()
                    .id(1L)
                    .name("certificate 1")
                    .description("description 1")
                    .price(new BigDecimal("1.10"))
                    .createDate(dateFormatISO8601.parse("2021-12-08T22:11:11.000Z"))
                    .lastUpdateDate(dateFormatISO8601.parse("2021-12-08T22:22:11.000Z"))
                    .duration(1)
                    .build();
            SECOND_CERTIFICATE = GiftCertificate.builder()
                    .id(2L)
                    .name("certificate 2")
                    .description("description 2")
                    .price(new BigDecimal("2.20"))
                    .createDate(dateFormatISO8601.parse("2021-12-08T23:22:22.000Z"))
                    .lastUpdateDate(dateFormatISO8601.parse("2021-12-08T23:22:22.000Z"))
                    .duration(2)
                    .build();
            THIRD_CERTIFICATE = GiftCertificate.builder()
                    .id(3L)
                    .name("certificate 3")
                    .description("description 3")
                    .price(new BigDecimal("3.30"))
                    .createDate(dateFormatISO8601.parse("2021-12-09T00:33:33.000Z"))
                    .lastUpdateDate(dateFormatISO8601.parse("2021-12-09T00:44:33.000Z"))
                    .duration(3)
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDaoImpl giftCertificateDaoImpl,
                                      GiftCertificateHasTagDao giftCertificateHasTagDao) {
        this.giftCertificateDao = giftCertificateDaoImpl;
        this.giftCertificateHasTagDao = giftCertificateHasTagDao;
    }

    @Test
    public void testCreateGiftCertificate_ShouldCreated() {
        giftCertificateDao.create(TEST_ENTITY_FOR_CREATE);
        Optional<GiftCertificate> giftCertificate =
                giftCertificateDao.findByName(TEST_ENTITY_FOR_CREATE.getName());
        assertTrue(giftCertificate.isPresent());
    }

    @Test
    public void testFindAllCertificate_ShouldBeFourCertificates() {
        List<GiftCertificate> actualList = Arrays.asList(FIRST_CERTIFICATE,
                SECOND_CERTIFICATE, THIRD_CERTIFICATE);
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAll();
        assertEquals(giftCertificateList, actualList);
    }


    @Test
    public void testCreateCertificateWithTag_ShouldAddNewReferenceOnTag() {
        GiftCertificateHasTag giftCertificateHasTag = GiftCertificateHasTag.builder()
                .tagId(THIRD_TAG.getId())
                .giftCertificateId(SECOND_CERTIFICATE.getId())
                .build();
        giftCertificateHasTagDao.create(giftCertificateHasTag);
        List<Long> tagIds = giftCertificateHasTagDao.findTagIdsByCertificateId(
                SECOND_CERTIFICATE.getId());
        assertTrue(tagIds.contains(THIRD_TAG.getId()));
    }

    @Test
    public void testFindTagIdsByCertificateId_ShouldFoundIds() {
        List<Long> tagIds = giftCertificateHasTagDao.findTagIdsByCertificateId(FIRST_TAG.getId());
        assertEquals(firstCertificateTags.stream()
                .map(Tag::getId).collect(Collectors.toList()), tagIds);
    }

    @Test
    public void testFindCertificateIdsByTagId_ShouldFoundIds() {
        List<Long> tagIds = giftCertificateHasTagDao.findTagIdsByCertificateId(SECOND_TAG.getId());
        assertEquals(SECOND_TAG.getId(), tagIds.get(0));
    }

    @Test
    public void testFindAllWithSorting_ShouldGetSortedList() {
        SortParamsContext sortParamsContext = new SortParamsContext(
                Collections.singletonList("id"), Collections.singletonList("DESC")
        );
        List<GiftCertificate> giftCertificateList =
                giftCertificateDao.findAllWithSorting(sortParamsContext);
        assertEquals(Arrays.asList(THIRD_CERTIFICATE, SECOND_CERTIFICATE, FIRST_CERTIFICATE),
                giftCertificateList);
    }

    @Test
    public void testFindAllWithFiltering_ShouldGetFilterList() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findAllWithFiltering(
                Arrays.asList(1L, 2L), "certif");
        assertEquals(Arrays.asList(FIRST_CERTIFICATE, SECOND_CERTIFICATE), giftCertificateList);
    }

    @Test
    public void testFindAllWithSortingAndFiltering_ShouldGetList() {
        SortParamsContext sortParamsContext = new SortParamsContext(
                Collections.singletonList("id"), Collections.singletonList("DESC")
        );
        List<GiftCertificate> giftCertificateList = giftCertificateDao
                .findAllWithSortingFiltering(sortParamsContext, Arrays.asList(1L, 2L), "certif");
        assertEquals(Arrays.asList(SECOND_CERTIFICATE, FIRST_CERTIFICATE), giftCertificateList);
    }

    @Test
    public void testFindById_ShouldGetEntity() {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(
                FIRST_CERTIFICATE.getId()
        );
        assertEquals(FIRST_CERTIFICATE, giftCertificate.get());
    }

    @Test
    public void testDeleteByIdShouldDelete() {
        giftCertificateDao.deleteById(THIRD_CERTIFICATE.getId());
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(THIRD_CERTIFICATE.getId());
        assertFalse(giftCertificate.isPresent());
    }
}
