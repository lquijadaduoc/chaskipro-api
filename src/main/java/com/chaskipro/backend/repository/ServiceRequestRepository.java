package com.chaskipro.backend.repository;

import com.chaskipro.backend.entity.ServiceRequest;
import com.chaskipro.backend.entity.EstadoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    
    List<ServiceRequest> findByClienteId(Long clienteId);
    
    List<ServiceRequest> findByProfesionalId(Long profesionalId);
    
    List<ServiceRequest> findByEstado(EstadoServicio estado);
    
    List<ServiceRequest> findByClienteIdAndEstado(Long clienteId, EstadoServicio estado);
    
    List<ServiceRequest> findByProfesionalIdAndEstado(Long profesionalId, EstadoServicio estado);
}
