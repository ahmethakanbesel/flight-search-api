package com.amadeus.api;

import com.amadeus.api.airport.AirportDTO;
import com.amadeus.api.airport.AirportService;
import com.amadeus.api.flight.FlightDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ApiApplicationTests {

    @Autowired
    private AirportService airportService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAirportWithoutAuthentication() throws Exception {
        AirportDTO airport = new AirportDTO();
        airport.setCity("New York");

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/v1/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testGetAirportWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/v1/airports/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testUpdateAirportWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(put("/v1/airports/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testDeleteAirportWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete("/v1/airports/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testGetAirportsWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/v1/airports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testCreateFlightWithoutAuthentication() throws Exception {
        FlightDTO flight = new FlightDTO();
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testGetFlightWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/v1/flights/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testUpdateFlightWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(put("/v1/flights/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testDeleteFlightWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete("/v1/flights/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testGetFlightsWithoutAuthentication() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/v1/flights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }
}
