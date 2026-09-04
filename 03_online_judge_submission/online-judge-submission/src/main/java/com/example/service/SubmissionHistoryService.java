package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.model.Submission;

@Service
public class SubmissionHistoryService {
    private final List<Submission> history = new ArrayList<>();

    public void addSubmission(Submission submission) {
        this.history.add(submission);
    }

    public List<Submission> getHistory() {
        return history;
    }

    public void printAllSubmissions() {
        if (history.isEmpty()) {
            System.out.println("\n[History] No submissions found.");
            return;
        }
        System.out.println("\n================ SUBMISSION HISTORY (" + history.size() + " Total) ================");
        for (int i = 0; i < history.size(); i++) {
            System.out.println("Submission #" + (i + 1) + ":");
            System.out.println(history.get(i));
            System.out.println("----------------------------------------------------------------");
        }
    }
}
