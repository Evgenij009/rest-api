package com.epam.esm.model.dto;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GiftCertificateDto {
    private GiftCertificate giftCertificate;
    private Set<Tag> certificateTags;
}
