package com.amadeus.api.flight;

import com.amadeus.api.airport.Airport;
import com.amadeus.api.airport.AirportRepository;
import com.amadeus.api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightService(final FlightRepository flightRepository,
            final AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    public List<FlightDTO> findAll() {
        final List<Flight> flights = flightRepository.findAll(Sort.by("id"));
        return flights.stream()
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .toList();
    }

    public FlightDTO get(final Long id) {
        return flightRepository.findById(id)
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FlightDTO flightDTO) {
        final Flight flight = new Flight();
        mapToEntity(flightDTO, flight);
        return flightRepository.save(flight).getId();
    }

    public void update(final Long id, final FlightDTO flightDTO) {
        final Flight flight = flightRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(flightDTO, flight);
        flightRepository.save(flight);
    }

    public void delete(final Long id) {
        flightRepository.deleteById(id);
    }

    private FlightDTO mapToDTO(final Flight flight, final FlightDTO flightDTO) {
        flightDTO.setId(flight.getId());
        flightDTO.setDepartureTime(flight.getDepartureTime());
        flightDTO.setArrivalTime(flight.getArrivalTime());
        flightDTO.setTicketPrice(flight.getTicketPrice());
        flightDTO.setTicketCurrency(flight.getTicketCurrency());
        flightDTO.setDepartureAirport(flight.getDepartureAirport() == null ? null : flight.getDepartureAirport().getId());
        flightDTO.setArrivalAirport(flight.getArrivalAirport() == null ? null : flight.getArrivalAirport().getId());
        return flightDTO;
    }

    private Flight mapToEntity(final FlightDTO flightDTO, final Flight flight) {
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setTicketPrice(flightDTO.getTicketPrice());
        flight.setTicketCurrency(flightDTO.getTicketCurrency());
        final Airport departureAirport = flightDTO.getDepartureAirport() == null ? null : airportRepository.findById(flightDTO.getDepartureAirport())
                .orElseThrow(() -> new NotFoundException("departureAirport not found"));
        flight.setDepartureAirport(departureAirport);
        final Airport arrivalAirport = flightDTO.getArrivalAirport() == null ? null : airportRepository.findById(flightDTO.getArrivalAirport())
                .orElseThrow(() -> new NotFoundException("arrivalAirport not found"));
        flight.setArrivalAirport(arrivalAirport);
        return flight;
    }

}
