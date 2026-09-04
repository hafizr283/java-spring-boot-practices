package com.example.model;

public class Submission {
    private static int totalSubmission = 0;
    private final String submissionId;
    private final String language;
    private final String sourceCode;
    private final int timeLimitMs;

    public Submission(String submissionId, String language, String sourceCode, int timeLimitMs) {
        if (submissionId == null)
            throw new IllegalArgumentException("Id can't be empty");
        if (sourceCode == null)
            throw new IllegalArgumentException("   - Source code cannot be empty");
        if (timeLimitMs <= 0)
            throw new IllegalArgumentException("Time limit must be positive");

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

    @Override
    public String toString() {
        return String.format("Submission Id: %s \n Language: %s\n time limits: %d\n source code: %s\n Total Submission : %d", getSubmissionId(),
                getLanguage(), getTimeLimitMs(), getSourceCode(), getTotalSubmission());
    }

}
