package com.klm.cases.df.fare.controller;

import com.klm.cases.df.fare.model.Fare;
import com.klm.cases.df.fare.service.FareService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fares/{origin}/{destination}")
@AllArgsConstructor
public class FareController {

    private FareService fareService;

    @GetMapping
    public ResponseEntity<Fare> getFare(@PathVariable("origin") final String origin,
                                        @PathVariable("destination") final String destination,
                                        @RequestParam(value = "currency", defaultValue = "EUR") final String currency) {
        return fareService.getFare(origin, destination, currency);
    }
}
