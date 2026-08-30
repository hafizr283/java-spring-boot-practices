package com.example.service;

import java.security.CodeSource;

import org.springframework.stereotype.Component;

import com.example.model.Submission;
import com.example.model.Verdict;

@Component("python")
public class PythonCodeRunner extends BaseCodeRunner {
    @Override
    public Verdict runCode(Submission submission) {
        validateSecurity(submission);
        return new Verdict("OK", 3000, 25, "Time Limit Excedeed with python");
    }
}
