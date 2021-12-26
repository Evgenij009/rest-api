package com.epam.esm.controller;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidParameterException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.link.TagLinkProvider;
import com.epam.esm.validator.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.util.RequestParams.*;

@RestController
@Profile("prod")
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagLinkProvider tagLinkProvider;

    @Autowired
    public TagController(TagService tagService, TagLinkProvider tagLinkProvider) {
        this.tagService = tagService;
        this.tagLinkProvider = tagLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Valid TagDto tagDto, BindingResult bindingResult)
            throws DuplicateEntityException {
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        TagDto newTagDto = tagService.create(tagDto);
        tagLinkProvider.provideLinks(newTagDto);
        return newTagDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> findAll(
            @RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size
    ) throws InvalidParameterException {
        RequestParametersValidator.validatePaginationParams(page, size);
        List<TagDto> tagDtoList = tagService.findAll(page, size);
        return tagDtoList.stream()
                .peek(tagLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findById(@PathVariable(ID_PATH_VARIABLE) long id) throws NotFoundEntityException {
        RequestParametersValidator.validateId(id);
        TagDto tagDto = tagService.findById(id);
        tagLinkProvider.provideLinks(tagDto);
        return tagDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        RequestParametersValidator.validateId(id);
        tagService.deleteById(id);
    }
}
