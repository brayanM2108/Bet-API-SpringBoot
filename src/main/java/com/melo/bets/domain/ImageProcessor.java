package com.melo.bets.domain;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageProcessor {
    MultipartFile compressImage(MultipartFile file) throws IOException;
    BufferedImage resizeImage(BufferedImage file) throws IOException;
}
