package com.epam.esm.validator;

import javax.validation.ValidationException;

public class RequestParametersValidator {
    public static final int NUMBER_MIN = 0;

    private RequestParametersValidator() {}

    public static void validatePaginationParams(int page, int size) {
        if (page < NUMBER_MIN || size <= NUMBER_MIN) {
            throw new ValidationException("validation.pagination");
        }
    }

    public static void validateId(long id) {
        if (id <= 0) {
            throw new ValidationException("validation.id");
        }
    }
}
