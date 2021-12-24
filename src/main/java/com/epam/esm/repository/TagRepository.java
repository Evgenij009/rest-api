package com.epam.esm.repository;

import com.epam.esm.model.entity.Tag;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag> {
    Optional<Tag> findByName(String name);
}
