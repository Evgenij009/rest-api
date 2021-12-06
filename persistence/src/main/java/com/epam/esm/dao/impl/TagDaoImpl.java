package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_ALL = "SELECT * FROM tag";
    private static final String SQL_CREATE_TAG = "INSERT INTO tag (name) VALUES(?)";
    private static final String SQL_FIND_TAG_BY_ID = "SELECT * FROM tag WHERE id=?";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_ID, new Object[]{id}, new TagMapper())
                .stream().findFirst();
    }
}
