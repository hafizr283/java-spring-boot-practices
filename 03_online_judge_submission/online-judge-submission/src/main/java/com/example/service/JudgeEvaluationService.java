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
    private final CodeRunner defaulCodeRunner;
    private final SubmissionHistoryService history;

    @Autowired
    public JudgeEvaluationService(Map<String, CodeRunner> runner,CodeRunner runner2,SubmissionHistoryService history) {
        this.runner = runner;
        this.defaulCodeRunner=runner2;
        this.history=history;
    }

    public Verdict validate(Submission submission) {
        // System.out.println(runner);
        history.addSubmission(submission);
        String lang = submission.getLanguage();
        CodeRunner currentRunner = runner.get(lang);
        System.out.println("Default Runner"+defaulCodeRunner);
        try {
            if (currentRunner != null) {
                
                return currentRunner.runCode(submission); 
            }

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
