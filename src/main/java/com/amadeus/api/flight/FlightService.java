package com.amadeus.api.flight;

import com.amadeus.api.airport.Airport;
import com.amadeus.api.airport.AirportRepository;
import com.amadeus.api.util.IllegalActionException;
import com.amadeus.api.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightService(final FlightRepository flightRepository,
                         final AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    public List<FlightDTO> searchOneWayFlights(String departureCity, String arrivalCity,
                                               LocalDate departureDay) {
        LocalDateTime departureTimeStart = departureDay.atStartOfDay();
        LocalDateTime departureTimeEnd = departureDay.plusDays(1).atStartOfDay();

        final List<Flight> flights = flightRepository.findByDepartureAirportCityAndArrivalAirportCityAndDepartureTimeBetween(
                departureCity, arrivalCity, departureTimeStart, departureTimeEnd);

        return flights.stream()
                .filter(flight -> flight.getReturnTime() == null)
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .toList();
    }

    public List<FlightDTO> searchRoundTripFlights(String departureCity, String arrivalCity,
                                                  LocalDate departureDay,
                                                  LocalDate returnDay) {
        LocalDateTime departureTimeStart = departureDay.atStartOfDay();
        LocalDateTime departureTimeEnd = departureDay.plusDays(1).atStartOfDay();

        LocalDateTime returnTimeStart = returnDay.atStartOfDay();
        LocalDateTime returnTimeEnd = returnDay.plusDays(1).atStartOfDay();

        final List<Flight> flights = flightRepository.findByDepartureAirportCityAndArrivalAirportCityAndDepartureTimeBetweenAndReturnTimeBetween(
                departureCity, arrivalCity, departureTimeStart, departureTimeEnd, returnTimeStart, returnTimeEnd);

        return flights.stream()
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .toList();
    }

    public List<FlightDTO> findAll() {
        final List<Flight> flights = flightRepository.findAll(Sort.by("id"));
        return flights.stream()
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .toList();
    }

    public List<FlightDTO> findAllByAirportId(Long airport) {
        final List<Flight> flights = flightRepository.findByDepartureAirportIdOrArrivalAirportId(airport, airport);
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
        if (Objects.equals(flightDTO.getArrivalAirport(), flightDTO.getDepartureAirport())) {
            throw new IllegalActionException("Departure and arrival airports cannot be the same");
        }
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
        flightDTO.setReturnTime(flight.getReturnTime());
        flightDTO.setTicketPrice(flight.getTicketPrice());
        flightDTO.setTicketCurrency(flight.getTicketCurrency());
        flightDTO.setDepartureAirport(flight.getDepartureAirport() == null ? null : flight.getDepartureAirport().getId());
        flightDTO.setArrivalAirport(flight.getArrivalAirport() == null ? null : flight.getArrivalAirport().getId());
        return flightDTO;
    }

    private Flight mapToEntity(final FlightDTO flightDTO, final Flight flight) {
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setReturnTime(flightDTO.getReturnTime());
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
