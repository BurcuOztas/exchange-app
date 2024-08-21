package com.example.exchangeapp.configuration;

import com.example.exchangeapp.entity.Conversion;
import com.example.exchangeapp.repository.ConversionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(ConversionRepository conversionRepository) {
        return args -> {
            Conversion conversion1 = Conversion.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .transactionDate(LocalDateTime.now())
                    .fromCurrency("EUR")
                    .toCurrency("USD")
                    .amount(100)
                    .convertedAmount(90)
                    .build();

            Conversion conversion2 = Conversion.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .transactionDate(LocalDateTime.now())
                    .fromCurrency("GBP")
                    .toCurrency("USD")
                    .amount(100)
                    .convertedAmount(130)
                    .build();

            conversionRepository.save(conversion1);
            conversionRepository.save(conversion2);
        };
    }
}
