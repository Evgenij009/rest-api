package com.epam.esm.service;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.TagMapperImpl;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TagServiceImpl.class, TagMapperImpl.class})
public class TagServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "tag";
    private static final Tag TAG = new Tag(ID, NAME);

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    @MockBean
    private TagRepositoryImpl tagDao;

    @MockBean
    private UserRepositoryImpl userRepository;

    @Autowired
    private TagServiceImpl tagService;

    @Test(expected = ValidationException.class)
    public void testGetAllShouldThrowsInvalidParametersExceptionWhenParamsInvalid() {
        tagService.findAllWithPagination(-3, -2);
    }

    @Test
    public void testGetByIdShouldGetWhenFound() {
        when(tagDao.findById(ID)).thenReturn(Optional.of(TAG));
        tagService.findById(ID);
        verify(tagDao).findById(ID);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testGetByIdShouldThrowsNoSuchEntityExceptionWhenNotFound() {
        when(tagDao.findById(ID)).thenReturn(Optional.empty());
        tagService.findById(ID);
    }

    @Test
    public void testDeleteByIdShouldDeleteWhenFound() {
        when(tagDao.findById(ID)).thenReturn(Optional.of(TAG));
        tagService.deleteById(ID);
        verify(tagDao).deleteById(ID);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testDeleteByIdShouldThrowsNoSuchEntityExceptionWhenNotFound() {
        when(tagDao.findById(ID)).thenReturn(Optional.empty());
        tagService.deleteById(ID);
    }
}