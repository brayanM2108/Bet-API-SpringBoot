package com.melo.bets.infrastructure.validation;

import com.melo.bets.domain.ImageValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageValidationService implements ImageValidator {

    /**
     * Sanitizes filename to be URL-safe by removing/replacing invalid characters
     */
    public String sanitizeFilename(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return "image.jpg";
        }

        // Replace spaces with underscores and remove other problematic characters
        String sanitized = originalFilename
                .replaceAll("\\s+", "_")           // Replace spaces with underscores
                .replaceAll("[^a-zA-Z0-9._-]", "") // Keep only alphanumeric, dots, underscores, and hyphens
                .toLowerCase();                     // Convert to lowercase for consistency

        // Ensure we have a valid extension
        if (!sanitized.contains(".")) {
            sanitized += ".jpg";
        }

        return sanitized;
    }

    public void validateFormat(MultipartFile imageFile) {
        String contentType = imageFile.getContentType();
        String originalFilename = imageFile.getOriginalFilename();

        // Validar por Content-Type
        if (contentType == null ||
                (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new IllegalArgumentException("Only JPEG and PNG images are allowed");
        }

        // Validar por extensión como respaldo
        if (originalFilename != null) {
            String extension = originalFilename.toLowerCase();
            if (!extension.endsWith(".jpg") && !extension.endsWith(".jpeg") && !extension.endsWith(".png")) {
                throw new IllegalArgumentException("Only .jpg, .jpeg and .png files are allowed");
            }
        }

    }

    public void validateSize(MultipartFile imageFile) {
        // Validar tamaño (opcional - 5MB máximo)
        if (imageFile.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Image size cannot exceed 5MB");
        }
    }

}
