package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftCertificateHasTag {
    private long giftCertificateId;
    private long tagId;
}