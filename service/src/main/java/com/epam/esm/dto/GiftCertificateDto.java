package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GiftCertificateDto {
    private GiftCertificate giftCertificate;
    private Set<Tag> certificateTags;
}
