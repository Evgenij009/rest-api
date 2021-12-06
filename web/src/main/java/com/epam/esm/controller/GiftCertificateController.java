package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.logic.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findAll() {
        return giftCertificateService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate findById(@PathVariable("id") long id) {
        return giftCertificateService.findById(id);
    }
}
