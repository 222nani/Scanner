package com.yourpackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourpackage.model.SearchResult;
import com.yourpackage.resources.SearchResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    private List<SearchResult> searchResults = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HoenScannerConfiguration> bootstrap) {
        // no initialization needed here
    }

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream carsStream = getClass().getResourceAsStream("/rental_cars.json")) {
            List<SearchResult> cars = mapper.readValue(carsStream, new TypeReference<List<SearchResult>>() {});
            searchResults.addAll(cars);
        }

        try (InputStream hotelsStream = getClass().getResourceAsStream("/hotels.json")) {
            List<SearchResult> hotels = mapper.readValue(hotelsStream, new TypeReference<List<SearchResult>>() {});
            searchResults.addAll(hotels);
        }

        environment.jersey().register(new SearchResource(searchResults));
    }
}
