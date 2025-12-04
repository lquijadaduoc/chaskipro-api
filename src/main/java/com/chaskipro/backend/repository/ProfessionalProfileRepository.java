package com.chaskipro.backend.repository;

import com.chaskipro.backend.entity.ProfessionalProfile;
import com.chaskipro.backend.entity.EstadoValidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long> {
    
    Optional<ProfessionalProfile> findByUserId(Long userId);
    
    List<ProfessionalProfile> findByEstadoValidacion(EstadoValidacion estadoValidacion);
    
    @Query("SELECT p FROM ProfessionalProfile p JOIN p.coberturas c WHERE c.id = :comunaId AND p.estadoValidacion = 'APROBADO'")
    List<ProfessionalProfile> findByComunaIdAndApproved(Long comunaId);
}
