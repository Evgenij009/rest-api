package com.epam.esm.repository;

import com.epam.esm.config.TestJpaConfig;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.util.SortParamsContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestJpaConfig.class)
@Transactional
class GiftCertificateRepositoryImplTest {
    private static final GiftCertificate CERTIFICATE_TO_CREATE = new GiftCertificate(
            "certificate new", "description new", new BigDecimal("1.10"), 1,
            LocalDateTime.parse("2020-01-01T01:11:11").atZone(ZoneId.of("Europe/Minsk")),
            LocalDateTime.parse("2021-01-01T01:22:11").atZone(ZoneId.of("Europe/Minsk")), null);
    private static final GiftCertificate FIRST_CERTIFICATE = new GiftCertificate(
            1L, "certificate 1", "description 1", new BigDecimal("1.10"), 1,
            LocalDateTime.parse("2020-01-01T01:11:11").atZone(ZoneId.of("Europe/Minsk")),
            LocalDateTime.parse("2021-01-01T01:22:11").atZone(ZoneId.of("Europe/Minsk")), null);
    private static final GiftCertificate SECOND_CERTIFICATE = new GiftCertificate(
            2L, "certificate 2", "description 2", new BigDecimal("2.20"), 2,
            LocalDateTime.parse("2020-02-02T02:22:22").atZone(ZoneId.of("Europe/Minsk")),
            LocalDateTime.parse("2021-02-02T02:33:22").atZone(ZoneId.of("Europe/Minsk")), null);
    private static final GiftCertificate THIRD_CERTIFICATE = new GiftCertificate(
            3L, "certificate 3", "description 3", new BigDecimal("3.30"), 3,
            LocalDateTime.parse("2020-03-03T03:33:33").atZone(ZoneId.of("Europe/Minsk")),
            LocalDateTime.parse("2021-03-03T03:44:33").atZone(ZoneId.of("Europe/Minsk")), null);
    private static final Tag FIRST_TAG = new Tag(1L, "tag 1");
    private static final Tag SECOND_TAG = new Tag(2L, "tag 2");
    private static final Tag THIRD_TAG = new Tag(3L, "tag 3");
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 25;

    static {
        FIRST_CERTIFICATE.setTagList(new ArrayList<>(Arrays.asList(FIRST_TAG, THIRD_TAG)));
        SECOND_CERTIFICATE.setTagList(new ArrayList<>(Collections.singletonList(SECOND_TAG)));
        THIRD_CERTIFICATE.setTagList(new ArrayList<>(Collections.singletonList(THIRD_TAG)));
    }

    @Autowired
    private GiftCertificateRepository certificateRepository;

    @Test
    public void testCreateCertificateShouldCreate() {
        GiftCertificate createdCertificate = certificateRepository.create(CERTIFICATE_TO_CREATE);

        Assertions.assertNotNull(createdCertificate);
    }

    @Test
    public void testGetAllShouldGet() {
        List<GiftCertificate> giftCertificates = certificateRepository
                .findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);

        Assertions.assertEquals(Arrays.asList(FIRST_CERTIFICATE, SECOND_CERTIFICATE, THIRD_CERTIFICATE),
                giftCertificates);
    }

    @Test
    public void testGetAllWithSortingFilteringShouldGetSortedCertificates() {
        SortParamsContext sortParamsContext = new SortParamsContext(
                Collections.singletonList("id"), Collections.singletonList("DESC"));
        List<GiftCertificate> giftCertificates = certificateRepository.findAllWithSortingFiltering(
                sortParamsContext, null, null, DEFAULT_PAGE, DEFAULT_SIZE);

        Assertions.assertEquals(Arrays.asList(THIRD_CERTIFICATE, SECOND_CERTIFICATE, FIRST_CERTIFICATE),
                giftCertificates);
    }

    @Test
    public void testGetAllWithFilteringShouldGetFilteredCertificates() {
        List<GiftCertificate> giftCertificates = certificateRepository.findAllWithSortingFiltering(null,
                Collections.singletonList(THIRD_TAG.getName()), "certif", DEFAULT_PAGE, DEFAULT_SIZE);

        Assertions.assertEquals(Arrays.asList(FIRST_CERTIFICATE, THIRD_CERTIFICATE), giftCertificates);
    }

    @Test
    public void testGetAllWithSortingFilteringShouldGet() {
        SortParamsContext sortParamsContext = new SortParamsContext(
                Collections.singletonList("id"), Collections.singletonList("DESC"));
        List<GiftCertificate> giftCertificates = certificateRepository.findAllWithSortingFiltering(
                sortParamsContext, Collections.singletonList(THIRD_TAG.getName()), "certif",
                DEFAULT_PAGE, DEFAULT_SIZE);

        Assertions.assertEquals(Arrays.asList(THIRD_CERTIFICATE, FIRST_CERTIFICATE), giftCertificates);
    }

    @Test
    public void testFindByIdShouldFind() {
        Optional<GiftCertificate> giftCertificate = certificateRepository.findById(
                FIRST_CERTIFICATE.getId());
        System.out.println("ONE " + FIRST_CERTIFICATE);
        System.out.println();
        System.out.println("TWO " + giftCertificate.get());
        Assertions.assertEquals(FIRST_CERTIFICATE, giftCertificate.get());
    }

    @Test
    public void testUpdateByIdShouldUpdate() {
        String savedName = FIRST_CERTIFICATE.getName();
        FIRST_CERTIFICATE.setName("new name");
        GiftCertificate updatedCertificate = certificateRepository.update(FIRST_CERTIFICATE);

        Assertions.assertEquals(updatedCertificate.getName(), "new name");

        FIRST_CERTIFICATE.setName(savedName);
    }

    @Test
    public void testDeleteByIdShouldDelete() {
        certificateRepository.deleteById(THIRD_CERTIFICATE.getId());

        boolean isExist = certificateRepository.findById(THIRD_CERTIFICATE.getId()).isPresent();
        Assertions.assertFalse(isExist);
    }
}