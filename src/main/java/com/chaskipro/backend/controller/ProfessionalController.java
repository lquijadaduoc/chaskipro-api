package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.ProfessionalProfileRequest;
import com.chaskipro.backend.dto.ProfessionalProfileResponse;
import com.chaskipro.backend.dto.UpdateStatusRequest;
import com.chaskipro.backend.entity.EstadoValidacion;
import com.chaskipro.backend.service.ProfessionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesionales")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    /**
     * Crear perfil profesional (Usuario profesional puede crear su propio perfil)
     */
    @PostMapping("/perfil/{userId}")
    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> crearPerfil(
            @PathVariable Long userId,
            @Valid @RequestBody ProfessionalProfileRequest request) {
        try {
            ProfessionalProfileResponse response = professionalService.crearPerfilProfesional(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualizar perfil profesional
     */
    @PutMapping("/perfil/{profileId}")
    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> actualizarPerfil(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfessionalProfileRequest request) {
        try {
            ProfessionalProfileResponse response = professionalService.actualizarPerfilProfesional(profileId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Buscar profesionales por comuna (Público - solo aprobados)
     */
    @GetMapping("/comuna/{comunaId}")
    public ResponseEntity<List<ProfessionalProfileResponse>> buscarPorComuna(@PathVariable Long comunaId) {
        try {
            List<ProfessionalProfileResponse> professionals = professionalService.buscarProfesionalesPorComuna(comunaId);
            return ResponseEntity.ok(professionals);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todos los profesionales (Solo Admin)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfessionalProfileResponse>> obtenerTodos() {
        List<ProfessionalProfileResponse> professionals = professionalService.obtenerTodosProfesionales();
        return ResponseEntity.ok(professionals);
    }

    /**
     * Obtener profesionales por estado de validación (Solo Admin)
     */
    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfessionalProfileResponse>> obtenerPorEstado(
            @PathVariable EstadoValidacion estado) {
        List<ProfessionalProfileResponse> professionals = professionalService.obtenerProfesionalesPorEstado(estado);
        return ResponseEntity.ok(professionals);
    }

    /**
     * Obtener perfil profesional por ID
     */
    @GetMapping("/perfil/{profileId}")
    public ResponseEntity<ProfessionalProfileResponse> obtenerPorId(@PathVariable Long profileId) {
        try {
            ProfessionalProfileResponse response = professionalService.obtenerPerfilPorId(profileId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener perfil profesional por ID de usuario
     */
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<ProfessionalProfileResponse> obtenerPorUserId(@PathVariable Long userId) {
        try {
            ProfessionalProfileResponse response = professionalService.obtenerPerfilPorUserId(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Aprobar perfil profesional (Solo Admin)
     */
    @PatchMapping("/perfil/{profileId}/aprobar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> aprobarPerfil(@PathVariable Long profileId) {
        try {
            ProfessionalProfileResponse response = professionalService.aprobarPerfil(profileId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Rechazar perfil profesional (Solo Admin)
     */
    @PatchMapping("/perfil/{profileId}/rechazar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> rechazarPerfil(@PathVariable Long profileId) {
        try {
            ProfessionalProfileResponse response = professionalService.rechazarPerfil(profileId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Cambiar estado de validación (Solo Admin) - Endpoint genérico
     */
    @PatchMapping("/perfil/{profileId}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> cambiarEstado(
            @PathVariable Long profileId,
            @Valid @RequestBody UpdateStatusRequest request) {
        try {
            ProfessionalProfileResponse response = professionalService.cambiarEstadoValidacion(profileId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
