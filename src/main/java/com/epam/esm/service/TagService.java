package com.epam.esm.service;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.entity.Tag;

import java.util.List;

/**
 * Business logic interface for Tags.
 */
public interface TagService {
    /**
     * Create new Tag.
     * @param tag Tag to create
     * @return id location of new Tag
     * @throws InvalidEntityException when Tag data is invalid
     * @throws DuplicateEntityException when Tag is already exist
     */
    long create(Tag tag);

    /**
     * Gets all Tags.
     *
     * @return Set of all Tags
     */
    List<Tag> findAll();

    /**
     * Gets Tag by id.
     *
     * @param id Tag id to search
     * @return founded Tag
     * @throws NotFoundEntityException when Tag is not found
     */
    Tag findById(long id);

    /**
     * Deletes Tag by id.
     *
     * @param id Tag id to search
     * @throws NotFoundEntityException when Tag is not found
     */
    void deleteById(long id);
}
