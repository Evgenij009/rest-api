package com.epam.esm.controller;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.UpdateGiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.link.GiftCertificateLinkProvider;
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
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateLinkProvider giftCertificateLinkProvider;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateLinkProvider giftCertificateLinkProvider) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateLinkProvider = giftCertificateLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody @Valid GiftCertificateDto giftCertificateDto,
                                     BindingResult bindingResult) throws NotFoundEntityException {
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        GiftCertificateDto newGiftCertificateDto = giftCertificateService.create(giftCertificateDto);
        giftCertificateLinkProvider.provideLinks(newGiftCertificateDto);
        return newGiftCertificateDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateById(
            @PathVariable(ID_PATH_VARIABLE) long id,
            @RequestBody @Valid UpdateGiftCertificateDto giftCertificate,
            BindingResult bindingResult) {
        RequestParametersValidator.validateId(id);
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        GiftCertificateDto giftCertificateDto = giftCertificateService.updateById(id, giftCertificate);
        giftCertificateLinkProvider.provideLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto replaceById(
            @PathVariable(ID_PATH_VARIABLE) long id,
            @RequestBody @Valid GiftCertificateDto giftCertificateDto,
            BindingResult bindingResult) throws NotFoundEntityException {
        RequestParametersValidator.validateId(id);
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        GiftCertificateDto replacedDto = giftCertificateService.replaceById(id, giftCertificateDto);
        giftCertificateLinkProvider.provideLinks(replacedDto);
        return replacedDto;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllByTags(
            @RequestParam(name = TAG_NAME, required = false) List<String> tagNames,
            @RequestParam(name = PART_NAME, required = false) String partName,
            @RequestParam(name = SORT, required = false) List<String> sortColumns,
            @RequestParam(name = ORDER, required = false) List<String> orderTypes,
            @RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size) {
        RequestParametersValidator.validatePaginationParams(page, size);
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateService
                .findBySearchParams(tagNames, partName, sortColumns, orderTypes, page, size);
        return giftCertificateDtoList.stream()
                .peek(giftCertificateLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findById(@PathVariable(ID_PATH_VARIABLE) long id) throws NotFoundEntityException {
        RequestParametersValidator.validateId(id);
        GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        giftCertificateLinkProvider.provideLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(ID_PATH_VARIABLE) long id) {
        RequestParametersValidator.validateId(id);
        giftCertificateService.deleteById(id);
    }
}
