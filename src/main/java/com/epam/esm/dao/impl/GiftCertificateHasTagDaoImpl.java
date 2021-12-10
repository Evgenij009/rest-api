package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateHasTagDao;
import com.epam.esm.model.entity.GiftCertificateHasTag;
import com.epam.esm.util.ColumnName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateHasTagDaoImpl implements GiftCertificateHasTagDao {
    private static final RowMapper<GiftCertificateHasTag> ROW_MAPPER =
            ((rs, rowNum) -> GiftCertificateHasTag.builder()
                    .giftCertificateId(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID))
                    .tagId(rs.getLong(ColumnName.TAG_ID))
                    .build());
    private static final String SQL_FIND_ALL =
            "SELECT tag_id, gift_certificate_id FROM gift_certificate_has_tag ";
    private static final String SQL_INSERT_GIFT_CERTIFICATE_HAS_TAG =
            "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES(?,?)";
    private static final String SQL_FIND_BY_GIFT_CERTIFICATE_ID =
            SQL_FIND_ALL + " WHERE gift_certificate_id=?";
    private static final String SQL_FIND_BY_TAG_ID =
            SQL_FIND_ALL + " WHERE tag_id=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateHasTagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(GiftCertificateHasTag giftCertificateHasTag) {
        jdbcTemplate.update(SQL_INSERT_GIFT_CERTIFICATE_HAS_TAG,
                giftCertificateHasTag.getGiftCertificateId(), giftCertificateHasTag.getTagId());
    }

    @Override
    public List<GiftCertificateHasTag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    @Override
    public List<Long> findCertificateIdsByTagId(long tagId) {
        return jdbcTemplate.query(SQL_FIND_BY_TAG_ID,
                (rs, i) -> rs.getLong(ColumnName.GIFT_CERTIFICATE_ID), tagId);
    }

    @Override
    public List<Long> findTagIdsByCertificateId(long certificateId) {
        return jdbcTemplate.query(SQL_FIND_BY_GIFT_CERTIFICATE_ID,
                (rs, i) -> rs.getLong(ColumnName.TAG_ID), certificateId);
    }
}
