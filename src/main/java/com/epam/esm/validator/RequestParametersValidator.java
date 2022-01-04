package com.epam.esm.validator;

import javax.validation.ValidationException;

public class RequestParametersValidator {
    private static final String VALIDATION_ID = "validation.id";
    private static final String VALIDATION_PAGINATION = "validation.pagination";
    public static final int NUMBER_MIN = 0;

    private RequestParametersValidator() {}

    public static void validatePaginationParams(int page, int size) {
        if (page < NUMBER_MIN || size <= NUMBER_MIN) {
            throw new ValidationException(VALIDATION_PAGINATION);
        }
    }

    public static void validateId(long id) {
        if (id <= 0) {
            throw new ValidationException(VALIDATION_ID);
        }
    }
}
