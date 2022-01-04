package com.epam.esm.service;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidEntityException;
import com.epam.esm.exception.InvalidParameterException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.dto.TagDto;

import java.util.List;

/**
 * Business logic interface for Tags.
 */
public interface TagService {
    /**
     * Create new Tag.
     * @param tagDto Tag to create
     * @return id location of new Tag
     * @throws InvalidEntityException when Tag data is invalid
     * @throws DuplicateEntityException when Tag is already exist
     */
    TagDto create(TagDto tagDto) throws DuplicateEntityException;

    List<TagDto> findAllWithPagination(int page, int size) throws InvalidParameterException;

    /**
     * Gets Tag by id.
     *
     * @param id Tag id to search
     * @return founded Tag
     * @throws NotFoundEntityException when Tag is not found
     */
    TagDto findById(long id) throws NotFoundEntityException;

    /**
     * Deletes Tag by id.
     *
     * @param id Tag id to search
     * @throws NotFoundEntityException when Tag is not found
     */
    void deleteById(long id) throws NotFoundEntityException;
    TagDto findMostWidelyUsedTagOfUserWhoMaxSpentMoney() throws NotFoundEntityException;
}
