package com.amadeus.api.airport;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AirportDTO {

    @Schema(type = "number", example = "")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Schema(type = "string", example = "Istanbul")
    private String city;

}
