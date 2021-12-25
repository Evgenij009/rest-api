package com.epam.esm.service.impl;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final String TAG_NOT_FOUND = "tag.not.found";
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          UserRepository userRepository,
                          TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(rollbackFor = DuplicateEntityException.class)
    public TagDto create(TagDto tagDto) throws DuplicateEntityException{
        String tagName = tagDto.getName();
        boolean isTagExist = tagRepository.findByName(tagName).isPresent();
        if (isTagExist) {
            throw new DuplicateEntityException("tag.exist");
        }
        Tag tag = tagMapper.mapToEntity(tagDto);
        return tagMapper.mapToDto(tagRepository.create(tag));
    }

    @Override
    public List<TagDto> findAll(int page, int size) {
        return tagMapper.mapListToDto(tagRepository.findAll(page, size));
    }

    @Override
    @Transactional
    public TagDto findById(long id) throws NotFoundEntityException {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException(TAG_NOT_FOUND);
        }
        return tagMapper.mapToDto(optionalTag.get());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException(TAG_NOT_FOUND);
        }
        tagRepository.deleteById(id);
    }
}
