package com.example.model;

public class Verdict {
    private final String status;
    private final int executionTimeMs;
    private final int memoryUsedKb;
    private final String message;

    public Verdict(String status, int executionTimeMs, int memoryUsedKb, String message) {
        this.status = status;
        this.executionTimeMs = executionTimeMs;
        this.memoryUsedKb = memoryUsedKb;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public int getExecutionTimeMs() {
        return executionTimeMs;
    }

    public int getMemoryUsedKb() {
        return memoryUsedKb;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("Code Status : %s\n Execution Time : %s \n Memory Used : %d KB\n Verdict : %s",
                getStatus(), getExecutionTimeMs(), getMemoryUsedKb(), getMessage());
    }

}
