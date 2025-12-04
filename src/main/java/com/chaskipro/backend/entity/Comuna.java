package com.chaskipro.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comunas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "profesionales")
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nombre de comuna es obligatorio")
    @Size(min = 2, max = 100, message = "Nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Región es obligatoria")
    @Size(min = 2, max = 100, message = "Región debe tener entre 2 y 100 caracteres")
    private String region;

    // Relación ManyToMany con ProfessionalProfile
    @ManyToMany(mappedBy = "coberturas")
    @Builder.Default
    private Set<ProfessionalProfile> profesionales = new HashSet<>();
}
