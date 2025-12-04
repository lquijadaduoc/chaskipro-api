package com.chaskipro.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRequest {
    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private Boolean disponible;
}
