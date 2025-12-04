package com.chaskipro.backend.dto;

import com.chaskipro.backend.entity.EstadoValidacion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStatusRequest {

    @NotNull(message = "Estado de validación es obligatorio")
    private EstadoValidacion estadoValidacion;

    private String motivoRechazo;
}
