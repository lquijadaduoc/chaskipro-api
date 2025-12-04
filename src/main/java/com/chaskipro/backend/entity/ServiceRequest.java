package com.chaskipro.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente es obligatorio")
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id")
    private ProfessionalProfile profesional;

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "Descripción del servicio es obligatoria")
    @Size(min = 10, max = 2000, message = "Descripción debe tener entre 10 y 2000 caracteres")
    private String descripcion;

    @Column(name = "fecha_solicitud", nullable = false)
    @NotNull(message = "Fecha de solicitud es obligatoria")
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_servicio")
    private LocalDateTime fechaServicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoServicio estado = EstadoServicio.SOLICITADO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuna_id", nullable = false)
    @NotNull(message = "Comuna es obligatoria")
    private Comuna comuna;

    @Column(length = 500)
    @Size(max = 500, message = "Dirección no puede exceder 500 caracteres")
    private String direccion;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Relación OneToOne con Review
    @OneToOne(mappedBy = "serviceRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;
}
