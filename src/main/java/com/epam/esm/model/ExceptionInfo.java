package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionInfo {
    private String errorMsg;
    private int errorCode;
}
