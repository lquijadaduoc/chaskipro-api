package com.chaskipro.backend.service;

import com.chaskipro.backend.dto.AuthResponse;
import com.chaskipro.backend.dto.LoginRequest;
import com.chaskipro.backend.dto.RegisterRequest;
import com.chaskipro.backend.entity.ProfessionalProfile;
import com.chaskipro.backend.entity.Rol;
import com.chaskipro.backend.entity.User;
import com.chaskipro.backend.repository.ProfessionalProfileRepository;
import com.chaskipro.backend.repository.UserRepository;
import com.chaskipro.backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProfessionalProfileRepository professionalProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validar que no exista el email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Validar que no exista el RUT
        if (userRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        // Crear usuario
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombreCompleto(request.getNombreCompleto())
                .rut(request.getRut())
                .rol(request.getRol())
                .activo(true)
                .build();

        user = userRepository.save(user);

        // Si es profesional, crear perfil profesional
        if (request.getRol() == Rol.PROFESIONAL) {
            ProfessionalProfile profile = ProfessionalProfile.builder()
                    .user(user)
                    .biografia(request.getBiografia())
                    .telefono(request.getTelefono())
                    .build();
            professionalProfileRepository.save(profile);
        }

        // Generar token
        String token = jwtUtils.generateToken(
                user.getEmail(),
                user.getRol().name(),
                user.getId()
        );

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getNombreCompleto(),
                user.getRol().name()
        );
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        try {
            // Autenticar usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Obtener usuario
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Generar token
            String token = jwtUtils.generateToken(
                    user.getEmail(),
                    user.getRol().name(),
                    user.getId()
            );

            return new AuthResponse(
                    token,
                    user.getId(),
                    user.getEmail(),
                    user.getNombreCompleto(),
                    user.getRol().name()
            );

        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }
}
