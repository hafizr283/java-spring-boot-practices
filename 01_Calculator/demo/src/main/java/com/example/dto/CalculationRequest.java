package com.example.dto;

public class CalculationRequest {
    private double num1;
    private double num2;
    private String op;
    public CalculationRequest(){}
    public void setNum1(double num1) {
        this.num1 = num1;
    }
    public void setNum2(double num2) {
        this.num2 = num2;
    }
    public void setOp(String op) {
        this.op = op;
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
}
