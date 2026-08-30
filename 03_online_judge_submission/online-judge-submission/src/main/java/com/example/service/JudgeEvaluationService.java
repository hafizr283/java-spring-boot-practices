package com.example.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.EvaluationException;
import com.example.model.Submission;
import com.example.model.Verdict;

@Service
public class JudgeEvaluationService {

    private final Map<String, CodeRunner> runner;

    @Autowired
    public JudgeEvaluationService(Map<String, CodeRunner> runner) {
        this.runner = runner;
    }

    public Verdict validate(Submission submission) {
        String lang = submission.getLanguage();
        CodeRunner currentRunner = runner.get(lang);
        try {
            if (currentRunner != null)
                return currentRunner.runCode(submission);
            else if (lang.isEmpty()) {
                System.out.println("No language is determined, moved to current runner c++");
                return runner.get("cpp").runCode(submission);
            } else
                throw new EvaluationException("Unsupported Language");

        } catch (EvaluationException e) {
            return new Verdict("rejected", 0, 0, e.getMessage());
        }
    }

}
