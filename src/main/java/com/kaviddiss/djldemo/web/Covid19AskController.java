package com.kaviddiss.djldemo.web;

import com.kaviddiss.djldemo.service.Covid19AskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Covid19AskController {
    private final Covid19AskService covid19AskService;

    public Covid19AskController(Covid19AskService covid19AskService) {
        this.covid19AskService = covid19AskService;
    }

    @GetMapping("/covid19/ask")
    public ResponseEntity ask(@RequestParam("q") String question) {
        String answer = covid19AskService.ask(question);
        return ResponseEntity.ok(Collections.singletonMap("answer", answer));
    }
}
