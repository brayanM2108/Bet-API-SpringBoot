package com.melo.bets.infrastructure.validation;

import com.melo.bets.domain.ImageProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ImageCompressionService implements ImageProcessor {


    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 1024;
    private static final float COMPRESSION_QUALITY = 0.8f;

    public MultipartFile compressImage(MultipartFile originalImage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(originalImage.getInputStream());

        // Redimensionar si es necesario
        BufferedImage resizedImage = resizeImage(bufferedImage);

        // Comprimir
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(COMPRESSION_QUALITY);

        writer.write(null, new IIOImage(resizedImage, null, null), param);

        ios.close();
        writer.dispose();

        byte[] compressedBytes = outputStream.toByteArray();

        return new CompressedMultipartFile(
                originalImage.getName(),
                originalImage.getOriginalFilename(),
                "image/jpeg",
                compressedBytes
        );
    }

    public BufferedImage resizeImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Calcular nuevas dimensiones manteniendo proporción
        if (width <= MAX_WIDTH && height <= MAX_HEIGHT) {
            return originalImage;
        }

        double widthRatio = (double) MAX_WIDTH / width;
        double heightRatio = (double) MAX_HEIGHT / height;
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (width * ratio);
        int newHeight = (int) (height * ratio);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics.dispose();

        return resizedImage;
    }
}
