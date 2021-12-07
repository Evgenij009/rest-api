package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UtilBuildQuery {
    private static final String COMMA_WITH_GAP = ", ";
    private static final String EQUALS_WITH_QUESTION = "=?";

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
}
