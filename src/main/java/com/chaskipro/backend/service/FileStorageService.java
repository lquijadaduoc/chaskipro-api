package com.chaskipro.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${storage.profile-images-path:/data/profile-images}")
    private String uploadDir;

    public String store(MultipartFile file, String prefix) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Archivo vacío");
        }
        try {
            String filename = prefix + "-" + UUID.randomUUID() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);
            Path target = dir.resolve(filename);
            file.transferTo(target);
            return filename;
        } catch (IOException e) {
            log.error("Error guardando archivo", e);
            throw new RuntimeException("No se pudo almacenar el archivo", e);
        }
    }
}
