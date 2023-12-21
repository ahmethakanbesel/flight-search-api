package com.amadeus.api.flight;

import com.amadeus.api.airport.AirportDTO;
import com.amadeus.api.airport.AirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FlightTasks {

    private static final Logger log = LoggerFactory.getLogger(FlightTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final FlightService flightService;
    private final AirportService airportService;

    public FlightTasks(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    //@Scheduled(cron = "0 0 * * *")
    @Scheduled(cron = "* * * * * *")
    public void retrieveFlights() {
        log.info("Retrieving flights from the API.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<FlightDTO> flights = mockApiResponse();
        log.info("Retrieved {} flights from the API.", flights.size());
        flights.forEach(flightService::create);
    }

    private List<FlightDTO> mockApiResponse() {
        List<FlightDTO> flights = new ArrayList<>();
        int numOfFlights = (int) (Math.random() * 10);

        for (int i = 0; i < numOfFlights; i++) {
            flights.add(createRandomFlight());
        }

        return flights;
    }

    private FlightDTO createRandomFlight() {
        final List<String> cities = List.of("New York", "Los Angeles", "Chicago", "Paris", "Istanbul", "Ankara", "Tehran");
        final List<String> currencies = List.of("USD", "EUR", "TRY", "IRR");

        // select 2 random cities
        int departureCityIndex = (int) (Math.random() * cities.size());
        int arrivalCityIndex = (int) (Math.random() * cities.size());
        while (arrivalCityIndex == departureCityIndex) {
            arrivalCityIndex = (int) (Math.random() * cities.size());
        }
        String departureCity = cities.get(departureCityIndex);
        String arrivalCity = cities.get(arrivalCityIndex);

        // create airports in the selected cities if they don't have any
        Long departureAirportId, arrivalAirportId;
        List<AirportDTO> departureAirports = airportService.findAllByCity(departureCity);
        List<AirportDTO> arrivalAirports = airportService.findAllByCity(arrivalCity);

        if (departureAirports.isEmpty()) {
            AirportDTO departureAirport = new AirportDTO();
            departureAirport.setCity(departureCity);
            departureAirportId = airportService.create(departureAirport);
        } else {
            departureAirportId = departureAirports.get(0).getId();
        }
        if (arrivalAirports.isEmpty()) {
            AirportDTO arrivalAirport = new AirportDTO();
            arrivalAirport.setCity(arrivalCity);
            arrivalAirportId = airportService.create(arrivalAirport);
        } else {
            arrivalAirportId = arrivalAirports.get(0).getId();
        }

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setDepartureAirport(departureAirportId);
        flightDTO.setArrivalAirport(arrivalAirportId);

        // set random departure and arrival times
        LocalDateTime departureTime = LocalDateTime.now().plusDays((int) (Math.random() * 10));
        LocalDateTime returnTime = departureTime.plusDays((int) (Math.random() * 10));

        flightDTO.setDepartureTime(departureTime);
        // 50% possibility for round trip flight
        if (Math.random() < 0.5) {
            flightDTO.setReturnTime(returnTime);
        }

        flightDTO.setTicketPrice(BigDecimal.valueOf((int) (Math.random() * 1000) / 10.0));
        flightDTO.setTicketCurrency(currencies.get((int) (Math.random() * currencies.size())));

        return flightDTO;
    }
}