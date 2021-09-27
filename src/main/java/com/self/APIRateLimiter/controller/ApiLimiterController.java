package com.self.APIRateLimiter.controller;

import com.self.APIRateLimiter.model.RequestWrapper;
import com.self.APIRateLimiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
public class ApiLimiterController {

    @Autowired
    RateLimiterService rateLimiterService;

    @GetMapping(value = "/resources/{userId}")
    public ResponseEntity<String> getResource(@PathVariable String userId){
        boolean result = rateLimiterService.handle(new RequestWrapper(userId));
        return result ? ResponseEntity.ok("Request submitted") : ResponseEntity.ok("Request terminated : Maximum request limit reached.");
    }
}
