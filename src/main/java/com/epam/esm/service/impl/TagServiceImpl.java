package com.epam.esm.service.impl;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private static final String TAG_NOT_FOUND = "tag.not.found";
    private static final String TAG_LIST_IS_EMPTY = "tag.list.is.empty";
    private static final String TAG_EXIST = "tag.exist";
    private static final String INVALID_PAGINATION = "validation.pagination";
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
    public TagDto create(TagDto tagDto) throws DuplicateEntityException {
        String tagName = tagDto.getName();
        if (tagRepository.findByName(tagName).isPresent()) {
            throw new DuplicateEntityException(TAG_EXIST);
        }
        Tag tag = tagMapper.mapToEntity(tagDto);
        return tagMapper.mapToDto(tagRepository.create(tag));
    }

    @Override
    public List<TagDto> findAllWithPagination(int page, int size) {
        RequestParametersValidator.validatePaginationParams(page, size);
        return tagMapper.mapListToDto(tagRepository.findAllWithPagination(page, size));
    }

    @Override
    @Transactional
    public TagDto findById(long id) throws NotFoundEntityException {
        return tagMapper.mapToDto(checkExistTagById(id));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        checkExistTagById(id);
        tagRepository.deleteById(id);
    }

    @Override
    public TagDto findMostWidelyUsedTagOfUserWhoMaxSpentMoney() throws NotFoundEntityException {
        User user = userRepository.findUserWithMaxSpentMoney();
        long theMostWidelyUsedTagId = user.getOrders().stream()
                .map(Order::getGiftCertificate)
                .map(GiftCertificate::getTagList)
                .flatMap(List<Tag>::stream)
                .collect(Collectors.groupingBy(Tag::getId, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NotFoundEntityException(TAG_LIST_IS_EMPTY));
        return tagMapper.mapToDto(tagRepository.findById(theMostWidelyUsedTagId)
                .orElseThrow(() -> new NotFoundEntityException(TAG_NOT_FOUND)));
    }

    private Tag checkExistTagById(long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException(TAG_NOT_FOUND);
        }
        return optionalTag.get();
    }
}
