package com.example.service;

import org.springframework.stereotype.Component;

import com.example.model.Submission;
import com.example.model.Verdict;

@Component("java")
public class JavaCodeRunner extends BaseCodeRunner {
    @Override
    public Verdict runCode(Submission submission) {
        validateSecurity(submission);
        return new Verdict("OK", 800, 32, "Accepted with OpenJDK");

    }

}
