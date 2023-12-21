package com.amadeus.api.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FlightDTO {

    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "2024-01-10 09:00")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "2024-01-14 19:30")
    private LocalDateTime returnTime;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    @Schema(type = "number", example = "49.08")
    private BigDecimal ticketPrice;

    @NotNull
    @Size(max = 3)
    @Schema(type = "string", example = "USD")
    private String ticketCurrency;

    @NotNull
    @Schema(type = "number", example = "10000")
    private Long departureAirport;

    @NotNull
    @Schema(type = "number", example = "10002")
    private Long arrivalAirport;

}
