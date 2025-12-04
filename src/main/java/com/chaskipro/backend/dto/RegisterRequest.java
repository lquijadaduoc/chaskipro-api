package com.chaskipro.backend.dto;

import com.chaskipro.backend.entity.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Email debe ser válido")
    private String email;

    @NotBlank(message = "Contraseña es obligatoria")
    @Size(min = 6, max = 50, message = "Contraseña debe tener entre 6 y 50 caracteres")
    private String password;

    @NotBlank(message = "Nombre completo es obligatorio")
    @Size(min = 3, max = 150, message = "Nombre debe tener entre 3 y 150 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "RUT es obligatorio")
    @Pattern(regexp = "^\\d{7,8}-[0-9Kk]$", message = "RUT debe tener formato válido (ej: 12345678-9)")
    private String rut;

    @NotNull(message = "Rol es obligatorio")
    private Rol rol;

    // Campos opcionales para profesionales
    private String biografia;

    @Pattern(regexp = "^\\+?56?[0-9]{9}$", message = "Teléfono debe ser válido para Chile")
    private String telefono;
}
