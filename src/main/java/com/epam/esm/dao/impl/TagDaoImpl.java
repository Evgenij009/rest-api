package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl implements TagDao {
    private static final RowMapper<Tag> ROW_MAPPER = new BeanPropertyRowMapper<>(Tag.class);
    private static final String SQL_FIND_ALL = "SELECT id, name FROM tags";
    private static final String SQL_INSERT_TAG = "INSERT INTO tags (name) VALUES(?)";
    private static final String SQL_FIND_TAG_BY_ID = "SELECT id, name FROM tags WHERE id=?";
    private static final String SQL_FIND_TAG_BY_NAME = "SELECT id, name FROM tags WHERE name=?";
    private static final String SQL_DELETE_TAG = "DELETE FROM tags WHERE id=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Tag tag) {
        jdbcTemplate.update(SQL_INSERT_TAG, tag.getName());
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_ID, ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_TAG_BY_NAME, ROW_MAPPER, name).stream().findAny();
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
    }
}
