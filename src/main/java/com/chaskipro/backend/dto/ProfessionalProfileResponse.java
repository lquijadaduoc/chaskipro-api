package com.chaskipro.backend.dto;

import com.chaskipro.backend.entity.EstadoValidacion;
import com.chaskipro.backend.entity.ProfessionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalProfileResponse {

    private Long id;
    private Long userId;
    private String nombreCompleto;
    private String email;
    private String biografia;
    private String telefono;
    private EstadoValidacion estadoValidacion;
    private ProfessionCategory categoria;
    private Boolean disponible;
    private String motivoRechazo;
    private BigDecimal promedioCalificacion;
    private Integer totalCalificaciones;
    private Integer serviciosCompletados;
    private Set<ComunaDTO> coberturas;
    private LocalDateTime fechaCreacion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ComunaDTO {
        private Long id;
        private String nombre;
        private String region;
    }
}
