package com.klm.cases.df.fare.controller;

import com.klm.cases.df.fare.model.Currency;
import com.klm.cases.df.fare.model.Fare;
import com.klm.cases.df.fare.service.FareService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FareController.class)
class FareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FareService fareService;

    private static final String API_URL = "/fares/AMS/BLR";

    @Test
    void getFare() throws Exception {

        String origin = "AMS";
        String destination = "BLR";
        double amount = 800.00;

        Fare fare = Fare.builder()
                .amount(amount)
                .destination(destination)
                .origin(origin)
                .currency(Currency.EUR)
                .build();

        when(fareService.getFare(origin, destination, Currency.EUR.toString())).thenReturn(ResponseEntity.ok(fare));

        mockMvc.perform(
                        get(API_URL)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.origin").value(origin))
                .andExpect(jsonPath("$.destination").value(destination))
                .andExpect(jsonPath("$.currency").value(Currency.EUR.toString()));
    }
}