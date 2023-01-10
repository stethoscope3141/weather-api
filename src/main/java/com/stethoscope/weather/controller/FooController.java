package com.stethoscope.weather.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@RequiredArgsConstructor
@Slf4j
public class FooController {
    @GetMapping()
    public void divideByZero() {
        int num = 0 / 0;
    }
}
