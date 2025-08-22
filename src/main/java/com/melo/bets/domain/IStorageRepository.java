package com.melo.bets.domain;


import org.springframework.web.multipart.MultipartFile;

public interface IStorageRepository {
    String uploadAndGetUrl(String fileName, MultipartFile file) throws Exception;
}
