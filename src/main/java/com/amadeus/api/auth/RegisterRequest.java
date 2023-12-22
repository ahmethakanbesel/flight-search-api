package com.amadeus.api.auth;

import com.amadeus.api.user.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(example = "test@mail.com")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;
    private String password;
    private Role role;
}
