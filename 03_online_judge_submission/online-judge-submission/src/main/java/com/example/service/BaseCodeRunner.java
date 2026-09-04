package com.example.service;

import com.example.exception.EvaluationException;
import com.example.model.Submission;
import com.example.model.Verdict;

public abstract class BaseCodeRunner implements CodeRunner {
    public final void validateSecurity(Submission submission) {
        if (submission.getSourceCode() == null) {
            throw new EvaluationException("No code present");
        }
        if (submission.getSourceCode().length() > 15)
            throw new EvaluationException("Source code limit (15) exceeded");
    }

}
