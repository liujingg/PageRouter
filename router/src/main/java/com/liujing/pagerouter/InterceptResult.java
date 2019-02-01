package com.liujing.pagerouter;

import androidx.annotation.Nullable;

public class InterceptResult {

    private static final InterceptResult SUCCESS = new InterceptResult(true, 0, null);

    private boolean success;
    private int errorCode;
    @Nullable
    private String errorMessage;

    private InterceptResult(boolean success, int errorCode, @Nullable String errorMessage) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static InterceptResult success() {
        return SUCCESS;
    }

    public static InterceptResult failed(int errorCode, @Nullable String errorMessage) {
        return new InterceptResult(false, errorCode, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailed() {
        return !success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }
}
