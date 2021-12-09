package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Tag.
 */
public interface TagDao {
    /**
     * Create new Tag.
     *
     * @param tag the Tag to create
     */
    void create(Tag tag);

    /**
     * Find all Tags.
     *
     * @return List of founded Tags
     */
    List<Tag> findAll();

    /**
     * Find Tag by id.
     *
     * @param id Tag id to find
     * @return Optional Tag - Tag if founded or Empty if not
     */
    Optional<Tag> findById(long id);

    /**
     * Find Tag by name.
     *
     * @param name Tag name to find
     * @return Optional Tag - Tag if founded or Empty if not
     */
    Optional<Tag> findByName(String name);

    /**
     * Delete Tag by id.
     *
     * @param id Tag id to find
     */
    void deleteById(long id);
}
