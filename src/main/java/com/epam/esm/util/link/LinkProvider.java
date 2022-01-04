package com.epam.esm.util.link;

import org.springframework.hateoas.RepresentationModel;

public interface LinkProvider<T extends RepresentationModel> {
    void provideLinks(T dto);
}
