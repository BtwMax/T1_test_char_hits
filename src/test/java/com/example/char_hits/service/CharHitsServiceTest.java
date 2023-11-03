package com.example.char_hits.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CharHitsServiceTest {

    private final CharHitsService hitsService;

    @Test
    public void getCorrectCountingCharHits() {
        String string = "aaaaabcccc";
        Map<Character, Integer> hits = hitsService.countingHits(string);
        Set<Integer> values1 = new HashSet<>(hits.values());
        Set<Integer> values2 = new HashSet<>();
        values2.add(5);
        values2.add(4);
        values2.add(1);

        Assertions.assertNotNull(hits);
        Assertions.assertEquals(hits.size(), 3);
        Assertions.assertEquals(values1.size(), values2.size());
        Assertions.assertEquals(values1, values2);
    }
}
