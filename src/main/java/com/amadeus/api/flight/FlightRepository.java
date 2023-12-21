package com.amadeus.api.flight;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportCityAndArrivalAirportCityAndDepartureTimeBetween(
            String departureCity, String arrivalCity,
            LocalDateTime departureTimeStart, LocalDateTime departureTimeEnd);

    List<Flight> findByDepartureAirportCityAndArrivalAirportCityAndDepartureTimeBetweenAndReturnTimeBetween(
            String departureCity, String arrivalCity,
            LocalDateTime departureTimeStart, LocalDateTime departureTimeEnd,
            LocalDateTime returnTimeStart, LocalDateTime returnTimeEnd);

    List<Flight> findByDepartureAirportIdOrArrivalAirportId(Long airport, Long airport1);
}
