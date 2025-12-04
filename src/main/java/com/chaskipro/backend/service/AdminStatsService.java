package com.chaskipro.backend.service;

import com.chaskipro.backend.dto.AdminStatsDto;
import com.chaskipro.backend.entity.EstadoServicio;
import com.chaskipro.backend.repository.ServiceRequestRepository;
import com.chaskipro.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class AdminStatsService {

    private final UserRepository userRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    @Transactional(readOnly = true)
    public AdminStatsDto obtenerStats() {
        long totalUsuarios = userRepository.count();

        YearMonth ahora = YearMonth.now();
        LocalDateTime desde = ahora.atDay(1).atStartOfDay();
        LocalDateTime hasta = ahora.atEndOfMonth().atTime(23, 59, 59);

        long trabajosMes = serviceRequestRepository.countByEstadoAndFechaActualizacionBetween(
                EstadoServicio.FINALIZADO, desde, hasta);

        return AdminStatsDto.builder()
                .totalUsuarios(totalUsuarios)
                .trabajosCompletadosMes(trabajosMes)
                .build();
    }
}
