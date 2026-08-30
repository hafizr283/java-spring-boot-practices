package com.example.judge;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.config.AppConfig;
import com.example.model.Submission;
import com.example.model.Verdict;
import com.example.service.JudgeEvaluationService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        JudgeEvaluationService judge = context.getBean(JudgeEvaluationService.class);
        Verdict ver = judge.validate(new Submission("001", "python", "This is a python code hah", 250));
        System.out.println(ver);
        System.out.println(ver.getStatus() + ver.getExecutionTimeMs() + ver.getMemoryUsedKb() + ver.getMessage());
        // System.out.println("Hello world!");
        context.close();
    }
}