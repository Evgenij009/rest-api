package com.epam.esm.validator;

import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<Tag> {
    private static final int NAME_MIN_LENGTH = 1;
    private static final int NAME_MAX_LENGTH = 64;

    @Override
    public boolean isValid(Tag item) {
        String name = item.getName();
        return name != null
                && name.length() > NAME_MIN_LENGTH
                && name.length() < NAME_MAX_LENGTH;
    }
}
