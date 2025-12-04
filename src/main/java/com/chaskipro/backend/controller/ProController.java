package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.AvailabilityRequest;
import com.chaskipro.backend.dto.ProfessionalDashboardDto;
import com.chaskipro.backend.service.ProfessionalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pro")
@RequiredArgsConstructor
@Tag(name = "Profesional", description = "Operaciones para el portal de profesionales")
public class ProController {

    private final ProfessionalService professionalService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('PROFESIONAL','ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ProfessionalDashboardDto dashboard(Authentication authentication) {
        return professionalService.obtenerDashboard(authentication.getName());
    }

    @PutMapping("/availability")
    @PreAuthorize("hasAnyRole('PROFESIONAL','ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> actualizarDisponibilidad(@Valid @RequestBody AvailabilityRequest request,
                                                         Authentication authentication) {
        professionalService.actualizarDisponibilidad(authentication.getName(), request.getDisponible());
        return ResponseEntity.noContent().build();
    }
}
