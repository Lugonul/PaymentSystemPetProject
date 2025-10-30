package com.meshakin.microservices.feign.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/payments/response")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    @PostMapping
    public String processPayment(@RequestBody String message) {
        System.out.println(">>> Controller received: " + message);
        System.out.println(">>> Controller finished processing");
        return "success";
    }
}