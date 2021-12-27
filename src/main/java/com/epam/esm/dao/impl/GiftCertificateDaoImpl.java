package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortParamsContext;
import com.epam.esm.util.UtilBuilderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final SimpleDateFormat dateFormatISO8601 =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final RowMapper<GiftCertificate> ROW_MAPPER =
            ((rs, rowNum) -> GiftCertificate.builder()
                    .id(rs.getLong(ColumnName.ID))
                    .name(rs.getString(ColumnName.NAME))
                    .description(rs.getString(ColumnName.DESCRIPTION))
                    .price(rs.getBigDecimal(ColumnName.PRICE))
                    .duration(rs.getInt(ColumnName.DURATION))
                    .createDate(new Date(rs.getTimestamp(ColumnName.CREATE_DATE).getTime()))
                    .lastUpdateDate(new Date(rs.getTimestamp(ColumnName.LAST_UPDATE_DATE).getTime()))
                    .build());
    private static final String SQL_FIND_ALL =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificates";
    private static final String SQL_INSERT_GIFT_CERTIFICATE =
            "INSERT INTO gift_certificates (name, description, price,  duration) VALUES(?,?,?,?)";
    private static final String SQL_FIND_GIFT_CERTIFICATE_BY_ID = SQL_FIND_ALL + " WHERE id=?";
    private static final String SQL_FIND_GIFT_CERTIFICATE_BY_NAME = SQL_FIND_ALL + " WHERE name=?";
    private static final String SQL_DELETE_GIFT_CERTIFICATE_BY_ID =
            "DELETE FROM gift_certificates WHERE id=?";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE_PART_QUERY =
            "UPDATE gift_certificates SET last_update_date=NOW(), ";
    private static final String SQL_WHERE_WITH_ID = " WHERE id=?";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_AND_WITH_GAP = "AND ";
    private static final String SQL_PART_QUERY_LIKE_NAME_AND_DESCRIPTION = "(name LIKE ? OR description LIKE ?)";
    private static final String GAP = " ";
    private final JdbcTemplate jdbcTemplate;
    private final UtilBuilderQuery utilBuilderQuery;

    static {
        dateFormatISO8601.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, UtilBuilderQuery utilBuildQuery) {
        this.jdbcTemplate = jdbcTemplate;
        this.utilBuilderQuery = utilBuildQuery;
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
        String query = SQL_FIND_ALL + utilBuilderQuery.buildSortingQuery(sortParamsContext);
        return jdbcTemplate.query(query, ROW_MAPPER);
    }

    @Override
    public List<GiftCertificate> findAllWithFiltering(List<Long> ids, String partInfo) {
        List<Object> values = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SQL_FIND_ALL).append(SQL_WHERE);
        fillFilterQueryData(ids, partInfo, queryBuilder, values);
        return jdbcTemplate.query(queryBuilder.toString(), ROW_MAPPER, values.toArray());
    }

    private void fillFilterQueryData(List<Long> ids, String partInfo,
                                     StringBuilder queryBuilder, List<Object> values) {
        if (ids != null && !ids.isEmpty()) {
            String queryFilter = utilBuilderQuery.buildFilteringQuery(ColumnName.ID, ids.size());
            queryBuilder.append(queryFilter);
            values.addAll(ids);
        }
        if (partInfo != null) {
            if (ids != null && !ids.isEmpty()) {
                queryBuilder.append(SQL_AND_WITH_GAP);
            }
            queryBuilder.append(SQL_PART_QUERY_LIKE_NAME_AND_DESCRIPTION);
            String regexPartInfo = utilBuilderQuery.buildRegexValue(partInfo);
            //add two identical (regexPartInfo) ones because in the request
            // to the database it will be like "name LIKE ? OR description LIKE ?".
            values.add(regexPartInfo);
            values.add(regexPartInfo);
        }
    }

    @Override
    public List<GiftCertificate> findAllWithSortingFiltering(
            SortParamsContext sortParamsContext, List<Long> ids, String partInfo) {
        List<Object> values = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SQL_FIND_ALL).append(SQL_WHERE);
        fillFilterQueryData(ids, partInfo, queryBuilder, values);
        queryBuilder.append(GAP).append(utilBuilderQuery.buildSortingQuery(sortParamsContext));
        return jdbcTemplate.query(queryBuilder.toString(), ROW_MAPPER, values.toArray());
    }

    @Override
    public void updateById(long id, Map<String, Object> giftCertificateInfo) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SQL_UPDATE_GIFT_CERTIFICATE_PART_QUERY);
        String query = utilBuilderQuery.buildUpdateAttributesQuery(giftCertificateInfo.keySet());
        queryBuilder.append(query);
        queryBuilder.append(SQL_WHERE_WITH_ID);
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
