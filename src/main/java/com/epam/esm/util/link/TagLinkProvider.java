package com.epam.esm.util.link;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagLinkProvider extends AbstractLinkProvider<TagDto> {
    private static final Class<TagController> CONTROLLER_CLASS = TagController.class;

    @Override
    public void provideLinks(TagDto dto) {
        long id = dto.getId();
        provideIdLinks(CONTROLLER_CLASS, dto, id, SELF_LINK, DELETE_LINK);
    }
}
