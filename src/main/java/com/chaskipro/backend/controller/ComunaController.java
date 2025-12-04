package com.chaskipro.backend.controller;

import com.chaskipro.backend.entity.Comuna;
import com.chaskipro.backend.service.ComunaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunas")
@RequiredArgsConstructor
public class ComunaController {

    private final ComunaService comunaService;

    /**
     * Obtener todas las comunas (Público)
     */
    @GetMapping
    public ResponseEntity<List<Comuna>> obtenerTodas() {
        List<Comuna> comunas = comunaService.obtenerTodasComunas();
        return ResponseEntity.ok(comunas);
    }

    /**
     * Obtener comunas por región (Público)
     */
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Comuna>> obtenerPorRegion(@PathVariable String region) {
        List<Comuna> comunas = comunaService.obtenerComunasPorRegion(region);
        return ResponseEntity.ok(comunas);
    }

    /**
     * Obtener comuna por ID (Público)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtenerPorId(@PathVariable Long id) {
        try {
            Comuna comuna = comunaService.obtenerComunaPorId(id);
            return ResponseEntity.ok(comuna);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear comuna (Solo Admin)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comuna> crear(@RequestParam String nombre, @RequestParam String region) {
        try {
            Comuna comuna = comunaService.crearComuna(nombre, region);
            return ResponseEntity.status(HttpStatus.CREATED).body(comuna);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
