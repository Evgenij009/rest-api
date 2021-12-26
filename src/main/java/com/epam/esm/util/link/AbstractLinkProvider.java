package com.epam.esm.util.link;

import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractLinkProvider<T extends RepresentationModel<T>> implements LinkProvider<T> {
    protected static final String SELF_LINK = "self";
    protected static final String DELETE_LINK = "delete";
    protected static final String UPDATE_LINK = "update";
    protected static final String REPLACE_LINK = "replace";

    private void provideIdLink(Class<?> controllerClass, T dto, long id, String linkName) {
        dto.add(linkTo(controllerClass).slash(id).withRel(linkName));
    }

    protected void provideIdLinks(Class<?> controllerClass, T dto, long id, String... linkNames) {
        for (String lineName : linkNames) {
            provideIdLink(controllerClass, dto, id, lineName);
        }
    }
}
