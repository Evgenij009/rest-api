package com.epam.esm.util;

import lombok.Value;

import java.util.List;

@Value
public class SortParamsContext {
    List<String> sortColumns;
    List<String> orderTypes;
}
