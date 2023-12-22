package com.amadeus.api.airport;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(
        name = "Airports API"
)
@RequestMapping(value = "/api/v1/airports", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirportController {

    private final AirportService airportService;

    public AirportController(final AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports(@RequestParam(required = false) String city) {
        if (city != null) {
            return ResponseEntity.ok(airportService.findAllByCity(city));
        }
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(airportService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAirport(@RequestBody @Valid final AirportDTO airportDTO) {
        final Long createdId = airportService.create(airportDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> updateAirport(@PathVariable(name = "id") final Long id,
                                              @RequestBody @Valid final AirportDTO airportDTO) {
        airportService.update(id, airportDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAirport(@PathVariable(name = "id") final Long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
