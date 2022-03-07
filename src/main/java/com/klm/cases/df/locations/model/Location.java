package com.klm.cases.df.locations.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("parent")
    private Location parent;
    @JsonProperty("children")
    private Set<Location> children;
}
