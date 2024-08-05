package com.edigest.journalApp.service;

import com.edigest.journalApp.api.response.WeatherResponse;
import com.edigest.journalApp.cache.AppCache;
import com.edigest.journalApp.constants.Placeholders;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;
    public WeatherResponse getWeather(String city){
        System.out.print("weather_of_"+city);
        WeatherResponse weatherResponse=redisService.get("weather_of_"+city, WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String finalApi = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(Placeholders.city, city).replace(Placeholders.apiKey, apiKey);
            ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body=response.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city, body,300l );
            }
            return body;
        }
    }
}
