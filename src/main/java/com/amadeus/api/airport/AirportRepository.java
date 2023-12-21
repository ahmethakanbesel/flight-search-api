package com.amadeus.api.airport;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findAllByCity(String city, Sort sort);
}
