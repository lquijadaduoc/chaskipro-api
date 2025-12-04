package com.chaskipro.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalDashboardDto {
    private long ticketsAbiertos;
    private long ticketsEnCurso;
    private boolean disponible;
}
