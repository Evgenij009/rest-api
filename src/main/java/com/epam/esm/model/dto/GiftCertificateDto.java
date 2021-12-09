package com.epam.esm.model.dto;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class GiftCertificateDto {
    private GiftCertificate giftCertificate;
    private Set<Tag> tags = new HashSet<>();

    @JsonCreator
    public GiftCertificateDto() {}
}
