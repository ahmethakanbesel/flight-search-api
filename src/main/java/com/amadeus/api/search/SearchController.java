package com.amadeus.api.search;

import com.amadeus.api.flight.FlightDTO;
import com.amadeus.api.flight.FlightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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
            @RequestParam()  String departureCity,
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam() String arrivalCity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        if (returnDate != null) {
            return ResponseEntity.ok(flightService.searchRoundTripFlights(departureCity, arrivalCity, departureDate, returnDate));
        }
        return ResponseEntity.ok(flightService.searchOneWayFlights(departureCity, arrivalCity, departureDate));
    }
}
