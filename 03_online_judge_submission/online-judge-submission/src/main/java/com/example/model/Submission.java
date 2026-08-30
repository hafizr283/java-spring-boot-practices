package com.example.model;

public class Submission {
    private static int totalSubmission=0;
    private final String submissionId;
    private final String language;
    private final String sourceCode;
    private final int timeLimitMs;
    public Submission(String submissionId, String language, String sourceCode, int timeLimitMs) {
        this.submissionId = submissionId;
        this.language = language;
        this.sourceCode = sourceCode;
        this.timeLimitMs = timeLimitMs;
        totalSubmission++;
    }
    public String getSubmissionId() {
        return submissionId;
    }
    public String getLanguage() {
        return language;
    }
    public String getSourceCode() {
        return sourceCode;
    }
    public int getTimeLimitMs() {
        return timeLimitMs;
    }
    public static int getTotalSubmission() {
        return totalSubmission;
    }
    

}
 