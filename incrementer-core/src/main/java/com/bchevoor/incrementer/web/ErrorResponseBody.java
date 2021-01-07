package com.bchevoor.incrementer.web;

public class ErrorResponseBody {

    private String reason;

    public String getReason() {
        return reason;
    }

    public ErrorResponseBody setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
