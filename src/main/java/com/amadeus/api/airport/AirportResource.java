package com.amadeus.api.airport;

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
        name = "Airports API"
)
@RequestMapping(value = "/api/v1/airports", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirportResource {

    private final AirportService airportService;

    public AirportResource(final AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(airportService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAirport(@RequestBody @Valid final AirportDTO airportDTO) {
        final Long createdId = airportService.create(airportDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAirport(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AirportDTO airportDTO) {
        airportService.update(id, airportDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAirport(@PathVariable(name = "id") final Long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }

}