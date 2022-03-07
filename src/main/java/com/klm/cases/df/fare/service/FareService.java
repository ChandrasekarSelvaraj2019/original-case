package com.klm.cases.df.fare.service;

import com.klm.cases.df.fare.model.Fare;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class FareService {

    private WebClient webClient;

    public ResponseEntity<Fare> getFare(String origin, String destination, String currency) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParam("currency", currency)
                        .path("fares/" + origin + "/" + destination)
                        .build())
                .retrieve()
                .toEntity(Fare.class)
                .block();
    }
}
