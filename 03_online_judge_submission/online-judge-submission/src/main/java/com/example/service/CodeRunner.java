package com.example.service;

import com.example.model.Submission;
import com.example.model.Verdict;

/**
 * CodeRunner
 */
public interface CodeRunner {

    Verdict runCode(Submission submission);
}