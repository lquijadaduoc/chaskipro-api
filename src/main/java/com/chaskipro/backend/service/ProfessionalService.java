package com.chaskipro.backend.service;

import com.chaskipro.backend.dto.ProfessionalProfileRequest;
import com.chaskipro.backend.dto.ProfessionalProfileResponse;
import com.chaskipro.backend.dto.UpdateStatusRequest;
import com.chaskipro.backend.entity.*;
import com.chaskipro.backend.repository.ComunaRepository;
import com.chaskipro.backend.repository.ProfessionalProfileRepository;
import com.chaskipro.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        profile = professionalProfileRepository.save(profile);

        return mapToResponse(profile);
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
                .promedioCalificacion(profile.getPromedioCalificacion())
                .totalCalificaciones(profile.getTotalCalificaciones())
                .serviciosCompletados(profile.getServiciosCompletados())
                .coberturas(comunasDTO)
                .fechaCreacion(profile.getFechaCreacion())
                .build();
    }
}
