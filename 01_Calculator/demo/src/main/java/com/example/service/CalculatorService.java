package com.example.service;

import com.example.dto.*;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public CalculationResponse calculate(CalculationRequest request) {
        double a = request.getNum1();
        double b = request.getNum2();
        String op = request.getOp();
        double result;
        switch (op) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;

            default:
                throw new IllegalArgumentException("Unsupported operator: " + op);
        }
        return new CalculationResponse(a, b, op, result);
    }

}
