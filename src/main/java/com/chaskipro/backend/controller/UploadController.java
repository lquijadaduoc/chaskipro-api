package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.UploadResponse;
import com.chaskipro.backend.service.FileStorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
@Tag(name = "Uploads", description = "Gestión de archivos de usuario")
public class UploadController {

    private final FileStorageService fileStorageService;

    @PostMapping("/profile-image")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UploadResponse> uploadProfileImage(@RequestParam("file") MultipartFile file,
                                                             Authentication authentication) {
        String stored = fileStorageService.store(file, authentication.getName());
        UploadResponse response = UploadResponse.builder()
                .filename(stored)
                .url("/uploads/profile-images/" + stored)
                .build();
        return ResponseEntity.ok(response);
    }
}
