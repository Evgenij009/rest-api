package com.epam.esm.logic;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    long create(Tag tag);
    List<Tag> findAll();
    Tag findById(long id);
    boolean deleteById(long id);
}
