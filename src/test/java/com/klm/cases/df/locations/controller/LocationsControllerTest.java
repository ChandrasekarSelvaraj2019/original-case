package com.klm.cases.df.locations.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klm.cases.df.locations.model.Location;
import com.klm.cases.df.locations.service.LocationsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationsController.class)
class LocationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationsService locationsService;

    private final ObjectMapper mapper = new ObjectMapper();
    private static final String API_URL = "/airports";


    @Test
    void getAllLocations() throws Exception {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(50, 1, 1048, 21);
        List<Location> locations = mapper.readValue(this.getClass()
                .getClassLoader()
                .getResourceAsStream("files/AllAiportLocations.json"), new TypeReference<List<Location>>() {
        });

        PagedModel pagedModel = PagedModel.of(locations, pageMetadata);

        when(locationsService.getAllLocations("en", 1, 50)).thenReturn(ResponseEntity.ok(pagedModel));

        mockMvc.perform(
                        get(API_URL + "?page=1&size=50")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.page.size").value(50))
                .andExpect(jsonPath("$.page.totalElements").value(1048))
                .andExpect(jsonPath("$.page.totalPages").value(21))
                .andExpect(jsonPath("$.page.number").value(1));
    }

    @Test
    void getLocation() throws Exception {
        Location location = mapper.readValue(this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("files/SpecificAirportLocation.json"),
                Location.class);

        when(locationsService.getLocation("en", "AAL")).thenReturn(ResponseEntity.ok(location));

        mockMvc.perform(
                        get(API_URL + "/AAL")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value(location.getCode()))
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.description").value(location.getDescription()));
    }

    @Test
    void findLocations() throws Exception {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(4, 1, 4, 1);
        List<Location> locations = mapper.readValue(this.getClass()
                .getClassLoader()
                .getResourceAsStream("files/MatchingAirportLocations.json"), new TypeReference<List<Location>>() {
        });

        PagedModel pagedModel = PagedModel.of(locations, pageMetadata);

        when(locationsService.findLocations("en", "AMS")).thenReturn(ResponseEntity.ok(pagedModel));

        mockMvc.perform(
                        get(API_URL + "?term=AMS")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.page.size").value(4))
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$.page.number").value(1));
    }
}