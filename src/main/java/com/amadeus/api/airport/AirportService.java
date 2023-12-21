package com.amadeus.api.airport;

import com.amadeus.api.flight.FlightDTO;
import com.amadeus.api.flight.FlightService;
import com.amadeus.api.util.IllegalActionException;
import com.amadeus.api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;


@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final FlightService flightService;

    public AirportService(AirportRepository airportRepository, FlightService flightService) {
        this.airportRepository = airportRepository;
        this.flightService = flightService;
    }

    public List<AirportDTO> findAll() {
        final List<Airport> airports = airportRepository.findAll(Sort.by("id"));
        return airports.stream()
                .map(airport -> mapToDTO(airport, new AirportDTO()))
                .toList();
    }

    public List<AirportDTO> findAllByCity(String city) {
        final List<Airport> airports = airportRepository.findAllByCity(city, Sort.by("id"));
        return airports.stream()
                .map(airport -> mapToDTO(airport, new AirportDTO()))
                .toList();
    }

    public AirportDTO get(final Long id) {
        return airportRepository.findById(id)
                .map(airport -> mapToDTO(airport, new AirportDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AirportDTO airportDTO) {
        final Airport airport = new Airport();
        mapToEntity(airportDTO, airport);
        return airportRepository.save(airport).getId();
    }

    public void update(final Long id, final AirportDTO airportDTO) {
        final Airport airport = airportRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(airportDTO, airport);
        airportRepository.save(airport);
    }

    public void delete(final Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("airport not found"));

        List<FlightDTO> associatedFlights = flightService.findAllByAirportId(airport.getId());
        if (!associatedFlights.isEmpty()) {
            throw new IllegalActionException("Cannot delete airport with associated flights.");
        }

        airportRepository.deleteById(id);
    }

    private AirportDTO mapToDTO(final Airport airport, final AirportDTO airportDTO) {
        airportDTO.setId(airport.getId());
        airportDTO.setCity(airport.getCity());
        return airportDTO;
    }

    private Airport mapToEntity(final AirportDTO airportDTO, final Airport airport) {
        airport.setCity(airportDTO.getCity());
        return airport;
    }
}
