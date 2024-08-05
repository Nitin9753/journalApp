package com.edigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Data
public class WeatherResponse {
    private Main main;
    @Data
    public class Main{
        public double temp;
        @JsonProperty("feels_like")
        public double feelsLike;
    }
}
