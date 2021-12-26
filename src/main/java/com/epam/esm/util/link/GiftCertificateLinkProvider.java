package com.epam.esm.util.link;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateLinkProvider extends AbstractLinkProvider<GiftCertificateDto> {
    private static final Class<GiftCertificateController> CONTROLLER_CLASS = GiftCertificateController.class;
    private final LinkProvider<TagDto> tagDtoLinkProvider;

    public GiftCertificateLinkProvider(LinkProvider<TagDto> tagDtoLinkProvider) {
        this.tagDtoLinkProvider = tagDtoLinkProvider;
    }

    @Override
    public void provideLinks(GiftCertificateDto dto) {
        long id = dto.getId();
        provideIdLinks(CONTROLLER_CLASS, dto, id, SELF_LINK, UPDATE_LINK, REPLACE_LINK, DELETE_LINK);
        if (dto.getTagList() != null) {
            dto.getTagList().forEach(tagDtoLinkProvider::provideLinks);
        }
    }
}
