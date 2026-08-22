package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CalculationRequest;
import com.example.dto.CalculationResponse;
import com.example.service.CalculatorService;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "*")
public class CalculatorController {
    public final CalculatorService cs;

    public CalculatorController(CalculatorService cs) {

        this.cs = cs;
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody CalculationRequest request) {
        try {
            CalculationResponse rs = cs.calculate(request);
            return ResponseEntity.ok(rs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
