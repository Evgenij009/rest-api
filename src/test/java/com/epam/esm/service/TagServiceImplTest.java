package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {
    private static final long ID = 1;
    private static final Tag TAG = new Tag(ID,"tag");

    private TagDao tagDao;
    private Validator<Tag> tagValidator;

    private TagServiceImpl tagService;

    @BeforeEach
    public void initMethod() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        tagValidator = Mockito.mock(TagValidator.class);
        tagService = new TagServiceImpl(tagDao, tagValidator);
    }

    @Test
    public void testCreateShouldCreateWhenValidAndNotExist() {
        when(tagValidator.isValid(any())).thenReturn(true);
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        tagService.create(TAG);
        verify(tagDao).create(TAG);
    }

    @Test
    public void testCreateShouldThrowsInvalidEntityExceptionWhenInvalid() {
        when(tagValidator.isValid(any())).thenReturn(false);
        assertThrows(InvalidEntityException.class, () -> tagService.create(TAG));
    }

    @Test
    public void testCreateShouldThrowsDuplicateEntityExceptionWhenExist() {
        when(tagValidator.isValid(any())).thenReturn(true);
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(TAG));
        assertThrows(DuplicateEntityException.class, () -> tagService.create(TAG));
    }

    @Test
    public void testFindAll_ShouldFindAll() {
        tagService.findAll();
        verify(tagDao).findAll();
    }

    @Test
    public void testFindById_ShouldGetWhenFound() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(TAG));
        tagService.findById(ID);
        verify(tagDao).findById(ID);
    }

    @Test
    public void testFindByIds_ShouldThrowsNotFoundEntityException() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> tagService.findById(ID));
    }

    @Test
    public void testDeleteById_ShouldDeletedWhenFound() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(TAG));
        tagService.deleteById(ID);
        verify(tagDao).deleteById(ID);
    }
}
