package com.kaviddiss.djldemo.web;

import com.kaviddiss.djldemo.service.MxNetCovid19Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class MxNetCovid19Controller {
    private final MxNetCovid19Service mxnetCovid19Service;

    public MxNetCovid19Controller(MxNetCovid19Service mxnetCovid19Service) {
        this.mxnetCovid19Service = mxnetCovid19Service;
    }

    @GetMapping("/covid19/ask")
    public ResponseEntity ask(@RequestParam("q") String question) {
        String answer = mxnetCovid19Service.ask(question);
        return ResponseEntity.ok(Collections.singletonMap("answer", answer));
    }
}
