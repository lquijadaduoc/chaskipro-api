package com.chaskipro.backend.repository;

import com.chaskipro.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Optional<Review> findByServiceRequestId(Long serviceRequestId);
    
    @Query("SELECT r FROM Review r WHERE r.serviceRequest.profesional.id = :profesionalId")
    List<Review> findByProfesionalId(Long profesionalId);
    
    boolean existsByServiceRequestId(Long serviceRequestId);
}
