package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final Validator<Tag> tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, Validator<Tag> tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public long create(Tag tag) {
        validateTag(tag);
        validateForExistTag(tag);
        tagDao.create(tag);
        Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
        return optionalTag.map(Tag::getId).orElse(-1L);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    @Transactional
    public Tag findById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException("tag.not.found");
        }
        return optionalTag.get();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException("tag.not.found");
        }
        tagDao.deleteById(id);
    }

    private void validateForExistTag(Tag tag) {
        if (tagDao.findByName(tag.getName()).isPresent()) {
            throw new DuplicateEntityException("tag.already.exist");
        }
    }

    private void validateTag(Tag tag) {
        if (!tagValidator.isValid(tag)) {
            throw new InvalidEntityException("tag.invalid");
        }
    }
}
