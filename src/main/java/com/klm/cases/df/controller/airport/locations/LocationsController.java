package com.klm.cases.df.controller.airport.locations;

import com.klm.cases.df.model.airport.Location;
import com.klm.cases.df.service.airport.locations.LocationsService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airports")
@AllArgsConstructor
public class LocationsController {

    private LocationsService locationsService;


    @GetMapping
    public ResponseEntity<PagedModel<Location>> getAllLocations(@RequestParam(value = "lang", defaultValue = "en") String lang, @RequestParam int page, @RequestParam int size) {
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
