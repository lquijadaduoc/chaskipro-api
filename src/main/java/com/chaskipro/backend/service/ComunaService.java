package com.chaskipro.backend.service;

import com.chaskipro.backend.entity.Comuna;
import com.chaskipro.backend.repository.ComunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunaService {

    private final ComunaRepository comunaRepository;

    @Transactional(readOnly = true)
    public List<Comuna> obtenerTodasComunas() {
        return comunaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Comuna> obtenerComunasPorRegion(String region) {
        return comunaRepository.findByRegion(region);
    }

    @Transactional(readOnly = true)
    public Comuna obtenerComunaPorId(Long id) {
        return comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
    }

    @Transactional
    public Comuna crearComuna(String nombre, String region) {
        if (comunaRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe una comuna con ese nombre");
        }

        Comuna comuna = Comuna.builder()
                .nombre(nombre)
                .region(region)
                .build();

        return comunaRepository.save(comuna);
    }
}
