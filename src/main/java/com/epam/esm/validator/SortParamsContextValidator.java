package com.epam.esm.validator;

import com.epam.esm.util.SortParamsContext;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.util.ColumnName.*;

public class SortParamsContextValidator {
    private static final List<String> availableColumns = new ArrayList<>(
            Arrays.asList(ID, NAME, DESCRIPTION, PRICE,
                    DURATION, CREATE_DATE, LAST_UPDATE_DATE)
    );
    private static final List<String> availableOrderTypes = new ArrayList<>(Arrays.asList("ASC", "DESC"));

    public static void validateParams(SortParamsContext item) {
        if (item.getSortColumns() != null) {
            for (String columnName : item.getSortColumns()) {
                if (!availableColumns.contains(columnName)) {
                    throw new ValidationException("validation.not.available.column");
                }
            }
        }
        if (item.getOrderTypes() != null) {
            for (String orderType : item.getOrderTypes()) {
                if (!availableOrderTypes.contains(orderType)) {
                    throw new ValidationException("validation.not.available.order");
                }
            }
        }
    }

    private SortParamsContextValidator() {}
}
