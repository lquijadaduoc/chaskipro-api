package com.chaskipro.backend.repository;

import com.chaskipro.backend.entity.EstadoValidacion;
import com.chaskipro.backend.entity.ProfessionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long>, JpaSpecificationExecutor<ProfessionalProfile> {
    
    Optional<ProfessionalProfile> findByUserId(Long userId);

    @Query("SELECT p FROM ProfessionalProfile p WHERE p.user.email = :email")
    Optional<ProfessionalProfile> findByUserEmail(String email);
    
    List<ProfessionalProfile> findByEstadoValidacion(EstadoValidacion estadoValidacion);

    Page<ProfessionalProfile> findByEstadoValidacion(EstadoValidacion estadoValidacion, Pageable pageable);
    
    @Query("SELECT p FROM ProfessionalProfile p JOIN p.coberturas c WHERE c.id = :comunaId AND p.estadoValidacion = 'APROBADO'")
    List<ProfessionalProfile> findByComunaIdAndApproved(Long comunaId);
}
