package com.chaskipro.backend.controller;

import com.chaskipro.backend.entity.Comuna;
import com.chaskipro.backend.service.ComunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunas")
@RequiredArgsConstructor
@Tag(name = "Comunas", description = "Gestión de comunas y regiones de Chile")
public class ComunaController {

    private final ComunaService comunaService;

    @Operation(summary = "Listar todas las comunas", description = "Obtiene el listado completo de comunas disponibles. Endpoint público.")
    @GetMapping
    public ResponseEntity<List<Comuna>> obtenerTodas() {
        List<Comuna> comunas = comunaService.obtenerTodasComunas();
        return ResponseEntity.ok(comunas);
    }

    @Operation(summary = "Buscar comunas por región", description = "Filtra comunas por nombre de región. Endpoint público.")
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Comuna>> obtenerPorRegion(
            @Parameter(description = "Nombre de la región", example = "Region Metropolitana") 
            @PathVariable String region) {
        List<Comuna> comunas = comunaService.obtenerComunasPorRegion(region);
        return ResponseEntity.ok(comunas);
    }

    @Operation(summary = "Obtener comuna por ID", description = "Busca una comuna específica por su ID. Endpoint público.")
    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtenerPorId(
            @Parameter(description = "ID de la comuna", example = "1") 
            @PathVariable Long id) {
        try {
            Comuna comuna = comunaService.obtenerComunaPorId(id);
            return ResponseEntity.ok(comuna);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear nueva comuna", 
               description = "Crea una nueva comuna en el sistema. Requiere rol ADMIN.",
               security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comuna> crear(
            @Parameter(description = "Nombre de la comuna", example = "Santiago Centro") 
            @RequestParam String nombre, 
            @Parameter(description = "Nombre de la región", example = "Region Metropolitana") 
            @RequestParam String region) {
        try {
            Comuna comuna = comunaService.crearComuna(nombre, region);
            return ResponseEntity.status(HttpStatus.CREATED).body(comuna);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
