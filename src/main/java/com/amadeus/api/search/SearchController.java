package com.amadeus.api.search;

import com.amadeus.api.flight.FlightDTO;
import com.amadeus.api.flight.FlightService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(
        name = "Search API"
)
@RequestMapping(value = "/api/v1/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    private final FlightService flightService;

    public SearchController(final FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights(
            @RequestParam() Long departureCity,
            @RequestParam() LocalDate departureDate,
            @RequestParam(required = false) Long arrivalCity,
            @RequestParam(required = false) LocalDate returnDate
    ) {
        return ResponseEntity.ok(flightService.findAll());
    }
}
