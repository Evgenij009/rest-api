package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_FIND_ALL = "SELECT * FROM gift_certificate";
    private static final String SQL_CREATE_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price,  duration, createDate, lastUpdateDate) VALUES(?,?,?,?,?,?)";
    private static final String SQL_FIND_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String SQL_FIND_GIFT_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE name=?";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(SQL_CREATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate());
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_GIFT_CERTIFICATE_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findFirst();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_GIFT_CERTIFICATE_BY_NAME, new Object[]{name},
                        new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findFirst();
    }
}
