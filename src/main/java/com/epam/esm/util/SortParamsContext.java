package com.epam.esm.util;

import java.util.List;

public class SortParamsContext {
    List<String> sortColumns;
    List<String> orderTypes;

    public SortParamsContext(List<String> sortColumns, List<String> orderTypes) {
        this.sortColumns = sortColumns;
        this.orderTypes = orderTypes;
    }

    public List<String> getSortColumns() {
        return sortColumns;
    }

    public void setSortColumns(List<String> sortColumns) {
        this.sortColumns = sortColumns;
    }

    public List<String> getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(List<String> orderTypes) {
        this.orderTypes = orderTypes;
    }
}
