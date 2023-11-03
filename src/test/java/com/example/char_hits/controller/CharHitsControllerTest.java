package com.example.char_hits.controller;

import com.example.char_hits.dto.IncomingString;
import com.example.char_hits.service.CharHitsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CharHitsControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    CharHitsService hitsService;

    IncomingString string;

    Map<Character, Integer> frequency;

    @BeforeEach
    void createString() {
        string = IncomingString.builder()
                .str("aaaaabbcccc")
                .build();
        String str = string.getStr();
        frequency = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));
    }

    @Test
    void getCountingCharHits() throws Exception {
        when(hitsService.countingHits(any())).thenReturn(frequency);

        mockMvc.perform(post("/hits")
                        .content(mapper.writeValueAsString(string))
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCountingCharHitsWithStringIsBlank() throws Exception {
        string.setStr(" ");
        when(hitsService.countingHits(any())).thenReturn(frequency);

        mockMvc.perform(post("/hits")
                        .content(mapper.writeValueAsString(string))
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCountingCharHitsWithStringIsNull() throws Exception {
        string.setStr(null);
        when(hitsService.countingHits(any())).thenReturn(frequency);

        mockMvc.perform(post("/hits")
                        .content(mapper.writeValueAsString(string))
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCountingCharHitsWithStringSizeIs0() throws Exception {
        string.setStr("");
        when(hitsService.countingHits(any())).thenReturn(frequency);

        mockMvc.perform(post("/hits")
                        .content(mapper.writeValueAsString(string))
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
