package com.chaskipro.backend.service;

import com.chaskipro.backend.dto.ProfessionalDashboardDto;
import com.chaskipro.backend.dto.ProfessionalSummaryDto;
import com.chaskipro.backend.entity.ProfessionalProfile;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalMapper {

    public ProfessionalSummaryDto toSummary(ProfessionalProfile profile) {
        String comunaPrincipal = profile.getCoberturas().stream()
                .findFirst()
                .map(c -> c.getNombre() + ", " + c.getRegion())
                .orElse(null);

        return ProfessionalSummaryDto.builder()
                .id(profile.getId())
                .nombreCompleto(profile.getUser().getNombreCompleto())
                .categoria(profile.getCategoria())
                .rating(profile.getPromedioCalificacion())
                .totalCalificaciones(profile.getTotalCalificaciones())
                .comunaPrincipal(comunaPrincipal)
                .disponible(Boolean.TRUE.equals(profile.getDisponible()))
                .build();
    }

    public ProfessionalDashboardDto toDashboard(ProfessionalProfile profile, long abiertos, long enCurso) {
        return ProfessionalDashboardDto.builder()
                .ticketsAbiertos(abiertos)
                .ticketsEnCurso(enCurso)
                .disponible(Boolean.TRUE.equals(profile.getDisponible()))
                .build();
    }
}
