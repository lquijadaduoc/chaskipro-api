package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.AuthResponse;
import com.chaskipro.backend.dto.LoginRequest;
import com.chaskipro.backend.dto.RegisterRequest;
import com.chaskipro.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro y autenticación de usuarios")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar nuevo usuario", 
               description = "Crea un nuevo usuario en el sistema. Si el rol es PROFESIONAL, también crea su perfil profesional.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                     content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse("Error al registrar usuario"));
        }
    }

    @Operation(summary = "Iniciar sesión", 
               description = "Autentica un usuario y devuelve un token JWT válido por 24 horas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso",
                     content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse("Error al iniciar sesión"));
        }
    }
}
