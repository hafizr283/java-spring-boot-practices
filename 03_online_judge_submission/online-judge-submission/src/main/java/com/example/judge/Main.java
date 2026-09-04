package com.example.judge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.config.AppConfig;
import com.example.model.Submission;
import com.example.model.Verdict;
import com.example.service.JudgeEvaluationService;
import com.example.service.SubmissionHistoryService;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/online_judge_db";
        String username = "root";
        String password = "9268";
        String sql = "select * from submissions";
        try (
                Connection con = DriverManager.getConnection(url, username, password);
                Statement st = con.createStatement();) {

            ResultSet rs = st.executeQuery(sql);
            rs.next();
            System.out.println(rs.getString("language"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        JudgeEvaluationService judge = context.getBean(JudgeEvaluationService.class);
        Verdict ver = judge.validate(new Submission("001", "python", "This is a python code hah", 250));
        System.out.println(ver.getStatus() + ver.getExecutionTimeMs() + ver.getMemoryUsedKb() + ver.getMessage());
        // System.out.println("Hello world!");
        ver = judge.validate(new Submission("002", "java", "public class Main {}", 1000));
        // System.out.println(ver.getStatus() + ver.getExecutionTimeMs() +
        // ver.getMemoryUsedKb() + ver.getMessage());
        System.out.println(ver);
        Submission submission;
        try {
            submission = new Submission("001", "cpp",
                    "#include<bits/stdc++.h> using namespace std; int main(){cout<<\"Md.hafizur Rahman\"", 250);
            System.out.println(submission);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        SubmissionHistoryService historyService = context.getBean(SubmissionHistoryService.class);
        historyService.printAllSubmissions();
        context.close();
    }
}