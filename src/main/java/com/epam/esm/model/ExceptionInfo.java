package com.epam.esm.model;

public class ExceptionInfo {
    private String errorMsg;
    private int errorCode;

    public ExceptionInfo(String errorMsg, int errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
