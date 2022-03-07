package com.klm.cases.df.locations.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klm.cases.df.client.WebClientConfig;
import com.klm.cases.df.locations.model.Location;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebClientConfig.class, LocationsService.class})
@TestPropertySource("classpath:application-test.properties")
class LocationsServiceTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private MockWebServer mockBackEnd;

    @Autowired
    private LocationsService locationsService;


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
    void getAllLocations() throws IOException, URISyntaxException {

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(50, 1, 1048, 21);
        List<Location> locations = mapper.readValue(this.getClass()
                .getClassLoader()
                .getResourceAsStream("files/AllAiportLocations.json"), new TypeReference<List<Location>>() {
        });

        PagedModel pagedModel = PagedModel.of(locations, pageMetadata);

        MockResponse mockResponse = new MockResponse()
                .setBody(mapper.writeValueAsString(pagedModel))
                .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(mockResponse);

        ResponseEntity<PagedModel<Location>> allLocations = locationsService.getAllLocations("en", 1, 50);
        assertNotNull(allLocations);

        PagedModel<Location> locationPagedModel = allLocations.getBody();
        assertNotNull(locationPagedModel);

        Collection<Location> locationCollection = locationPagedModel.getContent();
        assertEquals(50, locationCollection.size());
        PagedModel.PageMetadata metadata = locationPagedModel.getMetadata();
        assertNotNull(metadata);
        assertEquals(1, metadata.getNumber());
        assertEquals(50, metadata.getSize());

    }

    @Test
    void getLocation() throws IOException {
        Location location = mapper.readValue(this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("files/SpecificAirportLocation.json"),
                Location.class);

        MockResponse mockResponse = new MockResponse()
                .setBody(mapper.writeValueAsString(location))
                .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(mockResponse);

        ResponseEntity<Location> response = locationsService.getLocation("en", "AAL");
        assertNotNull(response);

        Location responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Aalborg", responseBody.getName());
        assertEquals("Aalborg - Aalborg (AAL), Denmark", responseBody.getDescription());
    }

    @Test
    void findLocations() throws IOException {

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(4, 1, 4, 1);
        List<Location> locations = mapper.readValue(this.getClass()
                .getClassLoader()
                .getResourceAsStream("files/MatchingAirportLocations.json"), new TypeReference<List<Location>>() {
        });

        PagedModel pagedModel = PagedModel.of(locations, pageMetadata);

        MockResponse mockResponse = new MockResponse()
                .setBody(mapper.writeValueAsString(pagedModel))
                .addHeader("Content-Type", "application/json");

        mockBackEnd.enqueue(mockResponse);


        ResponseEntity<PagedModel<Location>> responseEntity = locationsService.findLocations("en", "AMS");
        assertNotNull(responseEntity);

        PagedModel.PageMetadata metadata = responseEntity.getBody().getMetadata();
        assertNotNull(metadata);
        assertEquals(1, metadata.getNumber());
        assertEquals(4, metadata.getSize());
    }
}