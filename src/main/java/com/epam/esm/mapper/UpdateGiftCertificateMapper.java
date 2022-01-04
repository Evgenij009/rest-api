package com.epam.esm.mapper;

import com.epam.esm.model.dto.UpdateGiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateGiftCertificateMapper extends BaseMapper<UpdateGiftCertificateDto, GiftCertificate> {
}
