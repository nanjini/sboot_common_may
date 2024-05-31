package com.sboot.backend.common.core.api;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorDetails {
    private LocalDateTime timestamp;

    private String errorMessage;

    private String httpDetails;

    private List<String> fieldErrorMessages;

    public ErrorDetails(LocalDateTime timestamp, String errorMessage, String httpDetails) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
        this.httpDetails = httpDetails;
    }

    public ErrorDetails(LocalDateTime timestamp, String errorMessage, String httpDetails, List<String> fieldErrorMessages) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
        this.httpDetails = httpDetails;
        this.fieldErrorMessages = fieldErrorMessages;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String geterrorMessage() {
        return errorMessage;
    }

    public String getHttpDetails() {
        return httpDetails;
    }

    public List<String> getFieldErrorMessages() {
        return fieldErrorMessages;
    }
}
