package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    void create(Tag tag);
    List<Tag> findAll();
    Optional<Tag> findById(long id);
    Optional<Tag> findByName(String name);
    void deleteById(long id);
}
