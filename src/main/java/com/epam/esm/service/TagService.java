package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;

import java.util.List;

public interface TagService {
    long create(Tag tag);
    List<Tag> findAll();
    Tag findById(long id);
    void deleteById(long id);
}
