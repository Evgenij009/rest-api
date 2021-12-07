package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.util.UtilBuildQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final RowMapper<GiftCertificate> ROW_MAPPER =
            ((rs, rowNum) -> GiftCertificate.builder()
                    .id(rs.getLong(ColumnName.ID))
                    .name(rs.getString(ColumnName.NAME))
                    .description(rs.getString(ColumnName.DESCRIPTION))
                    .price(rs.getBigDecimal(ColumnName.PRICE))
                    .duration(rs.getInt(ColumnName.DURATION))
                    .createDate(rs.getTimestamp(ColumnName.CREATE_DATE))
                    .lastUpdateDate(rs.getTimestamp(ColumnName.LAST_UPDATE_DATE))
                    .build());
    private static final String SQL_FIND_ALL =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificates";
    private static final String SQL_INSERT_GIFT_CERTIFICATE =
            "INSERT INTO gift_certificates (name, description, price,  duration) VALUES(?,?,?,?)";
    private static final String SQL_FIND_GIFT_CERTIFICATE_BY_ID =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificates WHERE id=?";
    private static final String SQL_FIND_GIFT_CERTIFICATE_BY_NAME =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificates WHERE name=?";
    private static final String SQL_DELETE_GIFT_CERTIFICATE_BY_ID =
            "DELETE FROM gift_certificates WHERE id=?";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE_PART_QUERY = "UPDATE gift_certificates SET last_update_date=NOW(), ";
    private static final String SQL_WHERE = " WHERE id=?";
    private static final String SQL_FIND_CERTIFICATE_IDS_BY_TAG_ID =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
            "FROM gift_certificates WHERE name=?";
    private final JdbcTemplate jdbcTemplate;
    private final UtilBuildQuery utilBuildQuery;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, UtilBuildQuery utilBuildQuery) {
        this.jdbcTemplate = jdbcTemplate;
        this.utilBuildQuery = utilBuildQuery;
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(SQL_INSERT_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration());
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    @Override
    public List<GiftCertificate> findAllWithSorting(SortParamsContext sortParamsContext) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAllWithFiltering(List<Long> ids, String partInfo) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAllWithSortingFiltering(SortParamsContext sortParamsContext, List<Long> ids, String partInfo) {
        return null;
    }

    @Override
    public List<Long> findTagIdsByCertificateId(long certificateId) {
        return null;
    }

    @Override
    public List<Long> findCertificateIdsByTagId(long tagId) {
        return null;
    }

    @Override
    public void updateById(long id, Map<String, Object> giftCertificateInfo) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SQL_UPDATE_GIFT_CERTIFICATE_PART_QUERY);
        String query = utilBuildQuery.buildUpdateAttributesQuery(giftCertificateInfo.keySet());
        queryBuilder.append(query);
        queryBuilder.append(SQL_WHERE);
        List<Object> values = new ArrayList<>(giftCertificateInfo.values());
        values.add(id);
        jdbcTemplate.update(queryBuilder.toString(), values.toArray());
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_GIFT_CERTIFICATE_BY_ID, ROW_MAPPER, id).stream().findAny();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_GIFT_CERTIFICATE_BY_NAME, ROW_MAPPER, name).stream().findAny();
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE_BY_ID, id);
    }
}
