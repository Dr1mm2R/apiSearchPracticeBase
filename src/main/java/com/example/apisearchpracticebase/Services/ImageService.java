package com.example.apisearchpracticebase.Services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class ImageService {

    public String saveImage(MultipartFile file, String path) throws IOException {
        String filename = generateFilename(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(new File(path + filename));
        return filename;
    }

    private String generateFilename(String originalFilename) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return DigestUtils.md5Hex(originalFilename + timestamp) + getFileExtension(originalFilename);
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}
