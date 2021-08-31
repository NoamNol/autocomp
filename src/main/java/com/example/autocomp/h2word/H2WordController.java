package com.example.autocomp.h2word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/word")
public class H2WordController {
    private final H2WordService h2WordService;

    @Autowired
    public H2WordController(H2WordService h2WordService) {
        this.h2WordService = h2WordService;
    }

    @GetMapping
    public List<H2Word> getWords() {
        return h2WordService.getAllWords();
    }
}