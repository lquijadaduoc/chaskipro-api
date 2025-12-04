package com.chaskipro.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Contraseña es obligatoria")
    private String password;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    @NotBlank(message = "Nombre completo es obligatorio")
    @Size(min = 3, max = 150, message = "Nombre debe tener entre 3 y 150 caracteres")
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 12)
    @NotBlank(message = "RUT es obligatorio")
    @Pattern(regexp = "^\\d{7,8}-[0-9Kk]$", message = "RUT debe tener formato válido (ej: 12345678-9)")
    private String rut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Relación OneToOne con ProfessionalProfile (solo si es PROFESIONAL)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfessionalProfile professionalProfile;

    // Relación OneToMany con ServiceRequest (como cliente)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ServiceRequest> serviciosSolicitados = new ArrayList<>();
}
