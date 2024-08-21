package com.example.exchangeapp.controller;

import com.example.exchangeapp.dto.ExchangeRateResponse;
import com.example.exchangeapp.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ExchangeController.class)
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    void shouldReturnExchangeRate() throws Exception {
        ExchangeRateResponse response = ExchangeRateResponse.builder()
                .success(true)
                .base("EUR")
                .rates(Collections.singletonMap("USD", 1.1))
                .build();

        when(exchangeService.getExchangeRate(anyString(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/exchange/rate")
                        .param("fromCurrency", "EUR")
                        .param("toCurrency", "USD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.base", is("EUR")))
                .andExpect(jsonPath("$.rates.USD", is(1.1)));
    }
}