package com.chaskipro.backend.service;

import com.chaskipro.backend.dto.ProfessionalProfileRequest;
import com.chaskipro.backend.dto.ProfessionalProfileResponse;
import com.chaskipro.backend.dto.UpdateStatusRequest;
import com.chaskipro.backend.dto.ProfessionalDashboardDto;
import com.chaskipro.backend.dto.ProfessionalSummaryDto;
import com.chaskipro.backend.entity.*;
import com.chaskipro.backend.repository.ComunaRepository;
import com.chaskipro.backend.repository.ProfessionalProfileRepository;
import com.chaskipro.backend.repository.ServiceRequestRepository;
import com.chaskipro.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalProfileRepository professionalProfileRepository;
    private final UserRepository userRepository;
    private final ComunaRepository comunaRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ProfessionalMapper professionalMapper;

    @Transactional(readOnly = true)
    public Page<ProfessionalSummaryDto> listarPorEstado(EstadoValidacion estado, Pageable pageable) {
        return professionalProfileRepository.findByEstadoValidacion(estado, pageable)
                .map(professionalMapper::toSummary);
    }

    @Transactional
    public void cambiarEstado(Long profileId, EstadoValidacion estado, String motivoRechazo) {
        ProfessionalProfile profile = professionalProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado"));

        profile.setEstadoValidacion(estado);
        if (estado == EstadoValidacion.RECHAZADO) {
            profile.setMotivoRechazo(motivoRechazo);
        } else {
            profile.setMotivoRechazo(null);
        }

        professionalProfileRepository.save(profile);
    }

    /**
     * Crear un perfil profesional para un usuario existente
     */
    @Transactional
    public ProfessionalProfileResponse crearPerfilProfesional(Long userId, ProfessionalProfileRequest request) {
        // Verificar que el usuario existe y es PROFESIONAL
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getRol() != Rol.PROFESIONAL) {
            throw new RuntimeException("El usuario debe tener rol PROFESIONAL");
        }

        // Verificar que no tenga ya un perfil profesional
        if (professionalProfileRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un perfil profesional");
        }

        // Obtener las comunas de cobertura
        Set<Comuna> comunas = new HashSet<>();
        if (request.getComunaIds() != null && !request.getComunaIds().isEmpty()) {
            comunas = new HashSet<>(comunaRepository.findAllById(request.getComunaIds()));
            if (comunas.size() != request.getComunaIds().size()) {
                throw new RuntimeException("Algunas comunas no fueron encontradas");
            }
        }

        // Crear el perfil profesional
        ProfessionalProfile profile = ProfessionalProfile.builder()
                .user(user)
                .biografia(request.getBiografia())
                .telefono(request.getTelefono())
                .estadoValidacion(EstadoValidacion.PENDIENTE)
                .categoria(request.getCategoria() != null ? request.getCategoria() : ProfessionCategory.OTRO)
                .coberturas(comunas)
                .build();

        profile = professionalProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    /**
     * Actualizar perfil profesional existente
     */
    @Transactional
    public ProfessionalProfileResponse actualizarPerfilProfesional(Long profileId, ProfessionalProfileRequest request) {
        ProfessionalProfile profile = professionalProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado"));

        // Actualizar datos básicos
        if (request.getBiografia() != null) {
            profile.setBiografia(request.getBiografia());
        }
        if (request.getTelefono() != null) {
            profile.setTelefono(request.getTelefono());
        }
        if (request.getCategoria() != null) {
            profile.setCategoria(request.getCategoria());
        }

        // Actualizar coberturas si se proporcionan
        if (request.getComunaIds() != null) {
            Set<Comuna> comunas = new HashSet<>(comunaRepository.findAllById(request.getComunaIds()));
            if (comunas.size() != request.getComunaIds().size()) {
                throw new RuntimeException("Algunas comunas no fueron encontradas");
            }
            profile.setCoberturas(comunas);
        }

        profile = professionalProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    /**
     * Buscar profesionales por ID de Comuna (filtrando solo aprobados)
     */
    @Transactional(readOnly = true)
    public List<ProfessionalProfileResponse> buscarProfesionalesPorComuna(Long comunaId) {
        // Verificar que la comuna existe
        if (!comunaRepository.existsById(comunaId)) {
            throw new RuntimeException("Comuna no encontrada");
        }

        List<ProfessionalProfile> professionals = professionalProfileRepository
                .findByComunaIdAndApproved(comunaId);

        return professionals.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener todos los profesionales (para admin)
     */
    @Transactional(readOnly = true)
    public List<ProfessionalProfileResponse> obtenerTodosProfesionales() {
        return professionalProfileRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener profesionales por estado de validación
     */
    @Transactional(readOnly = true)
    public List<ProfessionalProfileResponse> obtenerProfesionalesPorEstado(EstadoValidacion estado) {
        return professionalProfileRepository.findByEstadoValidacion(estado).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtener perfil profesional por ID
     */
    @Transactional(readOnly = true)
    public ProfessionalProfileResponse obtenerPerfilPorId(Long profileId) {
        ProfessionalProfile profile = professionalProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado"));

        return mapToResponse(profile);
    }

    /**
     * Obtener perfil profesional por ID de usuario
     */
    @Transactional(readOnly = true)
    public ProfessionalProfileResponse obtenerPerfilPorUserId(Long userId) {
        ProfessionalProfile profile = professionalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("El usuario no tiene perfil profesional"));

        return mapToResponse(profile);
    }

    /**
     * Método para que el Admin apruebe o rechace un perfil profesional
     */
    @Transactional
    public ProfessionalProfileResponse cambiarEstadoValidacion(Long profileId, UpdateStatusRequest request) {
        ProfessionalProfile profile = professionalProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado"));

        // Validar que no se intente cambiar desde APROBADO a PENDIENTE
        if (profile.getEstadoValidacion() == EstadoValidacion.APROBADO 
            && request.getEstadoValidacion() == EstadoValidacion.PENDIENTE) {
            throw new RuntimeException("No se puede cambiar un perfil aprobado a pendiente");
        }

        profile.setEstadoValidacion(request.getEstadoValidacion());
        if (request.getEstadoValidacion() == EstadoValidacion.RECHAZADO) {
            profile.setMotivoRechazo(request.getMotivoRechazo());
        } else {
            profile.setMotivoRechazo(null);
        }

        profile = professionalProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    /**
     * Aprobar perfil profesional (método específico para Admin)
     */
    @Transactional
    public ProfessionalProfileResponse aprobarPerfil(Long profileId) {
        ProfessionalProfile profile = professionalProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado"));

        if (profile.getEstadoValidacion() == EstadoValidacion.APROBADO) {
            throw new RuntimeException("El perfil ya está aprobado");
        }

        profile.setEstadoValidacion(EstadoValidacion.APROBADO);
        profile.setMotivoRechazo(null);
        profile = professionalProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    /**
     * Rechazar perfil profesional
     */
    @Transactional
    public ProfessionalProfileResponse rechazarPerfil(Long profileId) {
        ProfessionalProfile profile = professionalProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado"));

        profile.setEstadoValidacion(EstadoValidacion.RECHAZADO);
        profile.setMotivoRechazo("Rechazado por administrador");
        profile = professionalProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    @Transactional(readOnly = true)
    public ProfessionalDashboardDto obtenerDashboard(String email) {
        ProfessionalProfile profile = professionalProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado para el usuario"));

        List<EstadoServicio> abiertos = List.of(EstadoServicio.SOLICITADO);
        List<EstadoServicio> enCurso = Arrays.asList(EstadoServicio.ACEPTADO, EstadoServicio.EN_PROCESO);

        long ticketsAbiertos = serviceRequestRepository.countByProfesionalIdAndEstadoIn(profile.getId(), abiertos);
        long ticketsEnCurso = serviceRequestRepository.countByProfesionalIdAndEstadoIn(profile.getId(), enCurso);

        return professionalMapper.toDashboard(profile, ticketsAbiertos, ticketsEnCurso);
    }

    @Transactional
    public void actualizarDisponibilidad(String email, boolean disponible) {
        ProfessionalProfile profile = professionalProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Perfil profesional no encontrado para el usuario"));
        profile.setDisponible(disponible);
        professionalProfileRepository.save(profile);
    }

    /**
     * Mapear entidad a DTO de respuesta
     */
    private ProfessionalProfileResponse mapToResponse(ProfessionalProfile profile) {
        Set<ProfessionalProfileResponse.ComunaDTO> comunasDTO = profile.getCoberturas().stream()
                .map(comuna -> ProfessionalProfileResponse.ComunaDTO.builder()
                        .id(comuna.getId())
                        .nombre(comuna.getNombre())
                        .region(comuna.getRegion())
                        .build())
                .collect(Collectors.toSet());

        return ProfessionalProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .nombreCompleto(profile.getUser().getNombreCompleto())
                .email(profile.getUser().getEmail())
                .biografia(profile.getBiografia())
                .telefono(profile.getTelefono())
                .estadoValidacion(profile.getEstadoValidacion())
                .categoria(profile.getCategoria())
                .disponible(profile.getDisponible())
                .motivoRechazo(profile.getMotivoRechazo())
                .promedioCalificacion(profile.getPromedioCalificacion())
                .totalCalificaciones(profile.getTotalCalificaciones())
                .serviciosCompletados(profile.getServiciosCompletados())
                .coberturas(comunasDTO)
                .fechaCreacion(profile.getFechaCreacion())
                .build();
    }
}
