package com.epam.esm.util.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.UserResponseDto;
import com.epam.esm.util.ColumnName;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserLinkProvider extends AbstractLinkProvider<UserResponseDto> {
    private static final Class<UserController> CONTROLLER_CLASS = UserController.class;

    @Override
    public void provideLinks(UserResponseDto dto) {
        long id = dto.getId();
        provideIdLinks(CONTROLLER_CLASS, dto, dto.getId(), SELF_LINK);
        dto.add(linkTo(CONTROLLER_CLASS)
                .slash(id)
                .slash(ColumnName.ORDERS)
                .withRel(ColumnName.ORDERS));
    }
}
