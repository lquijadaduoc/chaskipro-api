package com.chaskipro.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    @Builder.Default
    private String type = "Bearer";
    private Long id;
    private String email;
    private String nombreCompleto;
    private String rol;
    private String message;

    public AuthResponse(String token, Long id, String email, String nombreCompleto, String rol) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public AuthResponse(String message) {
        this.message = message;
    }
}
