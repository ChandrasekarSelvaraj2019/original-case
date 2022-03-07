package com.klm.cases.df.fare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klm.cases.df.client.WebClientConfig;
import com.klm.cases.df.fare.model.Currency;
import com.klm.cases.df.fare.model.Fare;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebClientConfig.class, FareService.class})
@TestPropertySource("classpath:application-test.properties")
class FareServiceTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private MockWebServer mockBackEnd;

    @Autowired
    private FareService fareService;

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8090);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getLocation() throws IOException {
        String origin = "AMS";
        String destination = "BLR";
        double amount = 800.00;

        Fare fare = Fare.builder()
                .amount(amount)
                .destination(destination)
                .origin(origin)
                .currency(Currency.EUR)
                .build();

        MockResponse mockResponse = new MockResponse()
                .setBody(mapper.writeValueAsString(fare))
                .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(mockResponse);

        ResponseEntity<Fare> response = fareService.getFare("BLR", "AMS", Currency.EUR.toString());
        assertNotNull(response);

        Fare responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(origin, responseBody.getOrigin());
        assertEquals(destination, responseBody.getDestination());
        assertEquals(amount, responseBody.getAmount());
        assertEquals(Currency.EUR, responseBody.getCurrency());
    }
}