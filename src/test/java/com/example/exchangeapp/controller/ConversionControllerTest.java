package com.example.exchangeapp.controller;

import com.example.exchangeapp.dto.ConversionRequest;
import com.example.exchangeapp.dto.ConversionResponse;
import com.example.exchangeapp.service.ConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ConversionController.class)
class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversionService conversionService;

    @Test
    void shouldConvertCurrency() throws Exception {
        ConversionRequest request = new ConversionRequest(100.0, "USD", "EUR");
        ConversionResponse response = new ConversionResponse("12345", 85.0);

        when(conversionService.convertCurrency(any(ConversionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/conversion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0, \"fromCurrency\": \"USD\", \"toCurrency\": \"EUR\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transactionId", is("12345")))
                .andExpect(jsonPath("$.convertedAmount", is(85.0)));
    }

    @Test
    void shouldReturnAllHistory() throws Exception {
        ConversionResponse response = new ConversionResponse("12345", 85.0);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ConversionResponse> page = new PageImpl<>(Collections.singletonList(response), pageable, 1);

        when(conversionService.getAllHistory(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/conversion/history/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].transactionId", is("12345")))
                .andExpect(jsonPath("$.content[0].convertedAmount", is(85.0)));
    }
}