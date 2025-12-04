package com.chaskipro.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false, unique = true)
    @NotNull(message = "Solicitud de servicio es obligatoria")
    private ServiceRequest serviceRequest;

    @Column(nullable = false)
    @NotNull(message = "Calificación es obligatoria")
    @Min(value = 1, message = "Calificación mínima es 1")
    @Max(value = 5, message = "Calificación máxima es 5")
    private Integer calificacion;

    @Column(length = 1000)
    @Size(max = 1000, message = "Comentario no puede exceder 1000 caracteres")
    private String comentario;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
