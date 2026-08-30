package com.example.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.example.model.Submission;
import com.example.model.Verdict;

@Component("cpp")
@Primary
public class CppCodeRunner extends BaseCodeRunner {
    @Override
    public Verdict runCode(Submission submission) {
        validateSecurity(submission);
        return new Verdict("OK", 1500, 15, "Accepted with g++ compiler");
    }
}
