package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UtilBuilderQuery {
    private static final Pattern UPPER_CASE_SYMBOL_PATTERN = Pattern.compile("[A-Z]");
    private static final String COMMA_WITH_GAP = ", ";
    private static final String GAP = " ";
    private static final String EQUALS_WITH_QUESTION = "=?";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String ASC = "ASC";
    private static final String QUESTION = "?";
    private static final String BRACKET_WITH_GAP = ") ";
    private static final String IN_WITH_BRACKET = " IN(";
    private static final String ANY_LINE_REGEX = "%";

    public String buildSortingQuery(SortParamsContext sortParamsContext) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ORDER_BY);
        List<String> sortColumns = convertToDatabaseFields(sortParamsContext.getSortColumns());
        List<String> orderTypes = sortParamsContext.getOrderTypes();
        for (int i = 0; i < sortColumns.size(); ++i) {
            if (i != 0) {
                stringBuilder.append(COMMA_WITH_GAP);
            }
            stringBuilder.append(sortColumns.get(i)).append(GAP);
            String typeOrdering = i < orderTypes.size() ? orderTypes.get(i) : ASC;
            stringBuilder.append(typeOrdering);
        }
        return stringBuilder.toString();
    }

    private List<String> convertToDatabaseFields(List<String> fields) {
        List<String> databaseFields = new ArrayList<>();
        for (String fieldName : fields) {
            Matcher matcher = UPPER_CASE_SYMBOL_PATTERN.matcher(fieldName);
            while (matcher.find()) {
                String matchedSymbol = matcher.group();
                fieldName = fieldName.replaceAll(matchedSymbol, "_" + matchedSymbol.toLowerCase());
            }
            databaseFields.add(fieldName);
        }
        return databaseFields;
    }

    public String buildUpdateAttributesQuery(Set<String> attributes) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean isFirstElement = true;
       for (String attribute : attributes) {
           if (!isFirstElement) {
               queryBuilder.append(COMMA_WITH_GAP);
           } else {
               isFirstElement = false;
           }
           queryBuilder.append(attribute);
           queryBuilder.append(EQUALS_WITH_QUESTION);
       }
       return queryBuilder.toString();
    }

    public String buildFilteringQuery(String columnName, int size) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(columnName).append(IN_WITH_BRACKET);
        for (int i = 0; i < size; ++i) {
            if (i != 0) {
                queryBuilder.append(COMMA_WITH_GAP);
            }
            queryBuilder.append(QUESTION);
        }
        queryBuilder.append(BRACKET_WITH_GAP);
        return queryBuilder.toString();
    }

    public String buildRegexValue(String value) {
        return String.format("%s%s%s", ANY_LINE_REGEX, value, ANY_LINE_REGEX);
    }
}
