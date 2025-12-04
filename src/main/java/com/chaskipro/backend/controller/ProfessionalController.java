package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.ProfessionalProfileRequest;
import com.chaskipro.backend.dto.ProfessionalProfileResponse;
import com.chaskipro.backend.dto.UpdateStatusRequest;
import com.chaskipro.backend.entity.EstadoValidacion;
import com.chaskipro.backend.service.ProfessionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Profesionales", description = "Gestión de perfiles profesionales y búsqueda")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @Operation(summary = "Crear perfil profesional", 
               description = "Crea un perfil profesional para un usuario. Requiere rol PROFESIONAL o ADMIN.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/perfil/{userId}")
    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> crearPerfil(
            @Parameter(description = "ID del usuario", example = "1") 
            @PathVariable Long userId,
            @Valid @RequestBody ProfessionalProfileRequest request) {
        try {
            ProfessionalProfileResponse response = professionalService.crearPerfilProfesional(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar perfil profesional", 
               description = "Actualiza la información de un perfil profesional. Requiere rol PROFESIONAL o ADMIN.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/perfil/{profileId}")
    @PreAuthorize("hasRole('PROFESIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ProfessionalProfileResponse> actualizarPerfil(
            @Parameter(description = "ID del perfil profesional", example = "1") 
            @PathVariable Long profileId,
            @Valid @RequestBody ProfessionalProfileRequest request) {
        try {
            ProfessionalProfileResponse response = professionalService.actualizarPerfilProfesional(profileId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar profesionales por comuna", 
               description = "Obtiene el listado de profesionales aprobados que operan en una comuna específica. Endpoint público.")
    @GetMapping("/comuna/{comunaId}")
    public ResponseEntity<List<ProfessionalProfileResponse>> buscarPorComuna(
            @Parameter(description = "ID de la comuna", example = "1") 
            @PathVariable Long comunaId) {
        try {
            List<ProfessionalProfileResponse> professionals = professionalService.buscarProfesionalesPorComuna(comunaId);
            return ResponseEntity.ok(professionals);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar todos los profesionales", 
               description = "Obtiene el listado completo de profesionales. Requiere rol ADMIN.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfessionalProfileResponse>> obtenerTodos() {
        List<ProfessionalProfileResponse> professionals = professionalService.obtenerTodosProfesionales();
        return ResponseEntity.ok(professionals);
    }

    @Operation(summary = "Buscar profesionales por estado", 
               description = "Filtra profesionales según su estado de validación (PENDIENTE, APROBADO, RECHAZADO). Requiere rol ADMIN.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfessionalProfileResponse>> obtenerPorEstado(
            @Parameter(description = "Estado de validación", example = "PENDIENTE") 
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
