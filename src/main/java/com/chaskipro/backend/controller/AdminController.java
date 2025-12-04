package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.AdminStatsDto;
import com.chaskipro.backend.dto.ProfessionalSummaryDto;
import com.chaskipro.backend.entity.EstadoValidacion;
import com.chaskipro.backend.service.AdminStatsService;
import com.chaskipro.backend.service.ProfessionalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Backoffice de profesionales y métricas")
public class AdminController {

    private final ProfessionalService professionalService;
    private final AdminStatsService adminStatsService;

    @GetMapping("/professionals/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public Page<ProfessionalSummaryDto> listarPendientes(Pageable pageable) {
        return professionalService.listarPorEstado(EstadoValidacion.PENDIENTE, pageable);
    }

    @PutMapping("/professionals/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> aprobar(@PathVariable Long id) {
        professionalService.cambiarEstado(id, EstadoValidacion.APROBADO, null);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/professionals/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> rechazar(@PathVariable Long id,
                                         @RequestParam(required = false) String motivo) {
        professionalService.cambiarEstado(id, EstadoValidacion.RECHAZADO, motivo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public AdminStatsDto stats() {
        return adminStatsService.obtenerStats();
    }
}
