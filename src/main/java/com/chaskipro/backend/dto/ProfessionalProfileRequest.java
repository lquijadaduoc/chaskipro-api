package com.chaskipro.backend.dto;

import com.chaskipro.backend.entity.ProfessionCategory;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalProfileRequest {

    @Size(max = 1000, message = "Biografía no puede exceder 1000 caracteres")
    private String biografia;

    @Pattern(regexp = "^\\+?56?[0-9]{9}$", message = "Teléfono debe ser válido para Chile")
    private String telefono;

    private ProfessionCategory categoria;

    // IDs de las comunas donde el profesional ofrece servicios
    private Set<Long> comunaIds;
}
