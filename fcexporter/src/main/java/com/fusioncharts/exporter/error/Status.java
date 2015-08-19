package com.fusioncharts.exporter.error;

public enum Status {
    SUCCESS(1, "Success"),
    FAILURE(0, "Failure");

    private final int statusCode;
    private final String statusMessage;

    private Status(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getCode() {
        return this.statusCode;
    }

    public String toString() {
        return this.statusMessage;
    }
}