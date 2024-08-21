package com.example.exchangeapp.controller;

import com.example.exchangeapp.dto.ConversionRequest;
import com.example.exchangeapp.dto.ConversionResponse;
import com.example.exchangeapp.service.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversion")
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService conversionService;

    @PostMapping
    public ConversionResponse convertCurrency(@RequestBody ConversionRequest request) {
        return conversionService.convertCurrency(request);
    }

    @GetMapping("/history/all")
    public Page<ConversionResponse> getAllHistory(
            @PageableDefault(sort = "transactionDate") Pageable pageable) {
        return conversionService.getAllHistory(pageable);
    }
}
