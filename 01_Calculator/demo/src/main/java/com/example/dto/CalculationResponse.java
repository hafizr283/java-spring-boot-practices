package com.example.dto;

public class CalculationResponse {
    private double num1;
    private double num2;
    private String op;
    private double result;

    public CalculationResponse(double num1, double num2, String op, double result) {

        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
        this.result = result;

    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public String getOp() {
        return op;
    }

    public double getResult() {
        return result;
    }
}
