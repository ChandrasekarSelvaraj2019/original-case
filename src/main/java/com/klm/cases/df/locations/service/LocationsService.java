package com.klm.cases.df.locations.service;

import com.klm.cases.df.locations.model.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@AllArgsConstructor
@Slf4j
public class LocationsService {

    private WebClient webClient;

    public ResponseEntity<PagedModel<Location>> getAllLocations(String lang, int page, int size) {
        log.debug("LocationsService called to retrieve all locations");
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParam("lang", lang)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .path("airports")
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<PagedModel<Location>>() {
                })
                .block();

    }

    public ResponseEntity<Location> getLocation(String lang, String key) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParam("lang", lang)
                        .path("airports/" + key)
                        .build())
                .retrieve()
                .toEntity(Location.class)
                .block();
    }


    public ResponseEntity<PagedModel<Location>> findLocations(String lang, String term) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .queryParam("lang", lang)
                        .queryParam("term", term)
                        .path("airports")
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<PagedModel<Location>>() {
                })
                .block();
    }


}
