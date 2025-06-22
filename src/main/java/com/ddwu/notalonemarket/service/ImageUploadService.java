package com.ddwu.notalonemarket.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile);

        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        return (String) uploadResult.get("secure_url");  // ✅ 클라우드 URL 반환
    }
}
