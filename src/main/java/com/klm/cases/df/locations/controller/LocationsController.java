package com.klm.cases.df.locations.controller;

import com.klm.cases.df.locations.model.Location;
import com.klm.cases.df.locations.service.LocationsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airports")
@AllArgsConstructor
@Slf4j
public class LocationsController {

    private LocationsService locationsService;


    @GetMapping
    public ResponseEntity<PagedModel<Location>> getAllLocations(@RequestParam(value = "lang", defaultValue = "en") String lang, @RequestParam int page, @RequestParam int size) {
        log.debug("Location API called to retrieve all locations");
        return locationsService.getAllLocations(lang, page, size);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Location> getLocation(@RequestParam(value = "lang", defaultValue = "en") String lang, @PathVariable("key") final String key) {
        return locationsService.getLocation(lang, key);
    }

    @GetMapping(params = "term")
    public ResponseEntity<PagedModel<Location>> findLocations(@RequestParam(value = "lang", defaultValue = "en") String lang, @RequestParam String term) {
        return locationsService.findLocations(lang, term);
    }


}
