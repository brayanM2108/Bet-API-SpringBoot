package com.melo.bets.infrastructure.storage;

import com.melo.bets.domain.IStorageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SupabaseStorageService implements IStorageRepository {

    @Value("${supabase.storage.bucket}")
    private String BUCKET;

    @Value("${supabase.storage.api-key}")
    private String API_KEY;

    @Value("${supabase.storage.url}")
    private String SUPABASE_URL;

    public String uploadAndGetUrl(String fileName, MultipartFile imageFile) throws Exception {
        Path tempFile = Files.createTempFile("bet-image-", ".tmp");
        imageFile.transferTo(tempFile.toFile());
        uploadImage(fileName, tempFile);
        Files.delete(tempFile);

        return "https://gksiwbiyvbinlkykpkib.storage.supabase.co/storage/v1/object/imagenes/" + fileName;
    }

    public void uploadImage(String fileName, Path imagePath) throws Exception {
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String url = SUPABASE_URL + BUCKET + "/" + fileName;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "image/jpeg")
                .PUT(HttpRequest.BodyPublishers.ofByteArray(imageBytes))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Error al subir la imagen: " + response.body());
        }
    }



}
