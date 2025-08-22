package com.melo.bets.domain;

import org.springframework.web.multipart.MultipartFile;

public interface ImageValidator {

    void validateFormat(MultipartFile file);
    void validateSize(MultipartFile file);
    String sanitizeFilename(String originalFilename);
}
