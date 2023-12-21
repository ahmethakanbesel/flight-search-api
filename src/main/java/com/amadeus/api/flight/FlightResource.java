package com.amadeus.api.flight;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(
        name = "Flights API"
)
@RequestMapping(value = "/api/v1/flights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightResource {

    private final FlightService flightService;

    public FlightResource(final FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(flightService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFlight(@RequestBody @Valid final FlightDTO flightDTO) {
        final Long createdId = flightService.create(flightDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFlight(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FlightDTO flightDTO) {
        flightService.update(id, flightDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFlight(@PathVariable(name = "id") final Long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
