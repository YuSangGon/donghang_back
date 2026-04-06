package com.main.donghang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("message", "동행 백엔드 연결 성공");
    }
}
