package com.amadeus.api.search;

import com.amadeus.api.flight.FlightDTO;
import com.amadeus.api.flight.FlightService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class SearchService {
    private final FlightService flightService;

    public SearchService(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<FlightDTO> findAllOneWay(Long departureCity, Long arrivalCity, LocalDate departureDate, Sort sort) {
        return flightService.findAll();
    }

    public List<FlightDTO> findAllRoundTrip(Long departureCity, Long arrivalCity, LocalDate departureDate, LocalDate returnDate, Sort sort) {
        return flightService.findAll();
    }

}
