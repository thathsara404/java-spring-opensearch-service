package com.thath.opensaerch.enums;

public enum ErrorMessage {

    NOT_FOUND("Requested resource not found"),
    UNEXPECTED_RESULT_FOUND("Unexpected result found");

    private String description;

    ErrorMessage (String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
