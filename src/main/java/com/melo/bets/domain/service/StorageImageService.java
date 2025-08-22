package com.melo.bets.domain.service;

import com.melo.bets.domain.IStorageRepository;
import com.melo.bets.domain.ImageProcessor;
import com.melo.bets.domain.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class StorageImageService {

    private final ImageValidator validator;
    private final ImageProcessor processor;
    private final IStorageRepository storage;

    @Autowired
    public StorageImageService(ImageValidator validator, ImageProcessor processor, IStorageRepository storage) {
        this.validator = validator;
        this.processor = processor;
        this.storage = storage;
    }

    public String processAndUploadImage(MultipartFile imageFile, String path) throws Exception {

        MultipartFile processedFile = imageFile;
        // 1. Validar formato
        validator.validateFormat(imageFile);

        //2. Validar tamaño inicial
        validator.validateSize(processedFile);

        // 3. Comprimir si es necesario

        if (imageFile.getSize() > 1024 * 1024) { // 1MB
            processedFile = processor.compressImage(imageFile);
        }

        // 4. Validar tamaño final
        validator.validateSize(processedFile);

        // 4. Generar nombre único y subir
        String fileName = path + "/" + UUID.randomUUID() + "-" +
                validator.sanitizeFilename(processedFile.getOriginalFilename());

        return storage.uploadAndGetUrl(fileName,processedFile );
    }


}
