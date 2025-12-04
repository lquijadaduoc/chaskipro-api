package com.chaskipro.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "professional_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 1000)
    @Size(max = 1000, message = "Biografía no puede exceder 1000 caracteres")
    private String biografia;

    @Column(length = 15)
    @Pattern(regexp = "^\\+?56?[0-9]{9}$", message = "Teléfono debe ser válido para Chile")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_validacion", nullable = false, length = 20)
    @Builder.Default
    private EstadoValidacion estadoValidacion = EstadoValidacion.PENDIENTE;

    @Column(name = "promedio_calificacion", precision = 3, scale = 2)
    @DecimalMin(value = "0.0", message = "Calificación no puede ser menor a 0")
    @DecimalMax(value = "5.0", message = "Calificación no puede ser mayor a 5")
    @Builder.Default
    private BigDecimal promedioCalificacion = BigDecimal.ZERO;

    @Column(name = "total_calificaciones")
    @Builder.Default
    private Integer totalCalificaciones = 0;

    @Column(name = "servicios_completados")
    @Builder.Default
    private Integer serviciosCompletados = 0;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Relación ManyToMany con Comuna (coberturas)
    @ManyToMany
    @JoinTable(
        name = "professional_coverage",
        joinColumns = @JoinColumn(name = "professional_profile_id"),
        inverseJoinColumns = @JoinColumn(name = "comuna_id")
    )
    @Builder.Default
    private Set<Comuna> coberturas = new HashSet<>();

    // Relación OneToMany con ServiceRequest (como profesional)
    @OneToMany(mappedBy = "profesional", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ServiceRequest> serviciosRealizados = new ArrayList<>();
}
