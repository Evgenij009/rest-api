package com.epam.esm.exception;

import com.epam.esm.util.ColumnName;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

public class ValidationExceptionChecker {
    public static void checkDtoValid(BindingResult bindingResult) {
        String msg = "";
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors(ColumnName.NAME)) {
                msg = "validation.name.invalid";
            }
            if (bindingResult.hasFieldErrors(ColumnName.DESCRIPTION)) {
                msg ="validation.description.invalid";
            }
            if (bindingResult.hasFieldErrors(ColumnName.PRICE)) {
                msg ="validation.price.invalid";
            }
            if (bindingResult.hasFieldErrors(ColumnName.DURATION)) {
                msg = "validation.duration.invalid";
            }
            if (bindingResult.hasFieldErrors(ColumnName.USER_ID)) {
                msg = "validation.id.invalid";
            }
            if (bindingResult.hasFieldErrors(ColumnName.CERTIFICATE_ID)) {
                msg ="validation.id.invalid";
            }
            if (bindingResult.hasFieldErrors(ColumnName.LOGIN)){
                msg ="validation.login.invalid";
            }
            throw new ValidationException(msg);
        }
    }

    private ValidationExceptionChecker() {}
}
