package com.klm.cases.df.fare.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fare {
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("currency")
    private Currency currency;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("destination")
    private String destination;

}
