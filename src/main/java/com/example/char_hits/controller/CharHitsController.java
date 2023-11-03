package com.example.char_hits.controller;

import com.example.char_hits.dto.IncomingString;
import com.example.char_hits.service.CharHitsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class CharHitsController {

    private final CharHitsService hitsService;

    @Autowired
    public CharHitsController(CharHitsService hitsService) {
        this.hitsService = hitsService;
    }

    @PostMapping("/hits")
    public Map<Character, Integer> charHits(@RequestBody @Valid IncomingString str) {
        String string = str.getStr();
        return hitsService.countingHits(string);
    }
}
