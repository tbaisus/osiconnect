package com.service.osiconnect.service;

import org.springframework.web.multipart.MultipartFile;


public interface StorageService {

    public String uploadFile(MultipartFile file,Long requestId);

    public byte[] downloadFile(String key);

    public String deleteFile(String fileName);

}
