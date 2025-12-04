package com.chaskipro.backend.dto;

import com.chaskipro.backend.entity.ProfessionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalSummaryDto {
    private Long id;
    private String nombreCompleto;
    private ProfessionCategory categoria;
    private BigDecimal rating;
    private Integer totalCalificaciones;
    private String comunaPrincipal;
    private Boolean disponible;
}
