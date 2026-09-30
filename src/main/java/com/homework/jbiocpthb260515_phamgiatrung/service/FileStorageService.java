package com.homework.jbiocpthb260515_phamgiatrung.service;

import com.homework.jbiocpthb260515_phamgiatrung.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Set<String> ALLOWED = Set.of("jpg", "jpeg", "png", "gif", "webp");
    private final Path root;

    public FileStorageService(@Value("${app.upload-dir:uploads}") String dir) throws IOException {
        this.root = Paths.get(dir).toAbsolutePath().normalize();
        Files.createDirectories(root);
    }

    public Path getRoot() {
        return root;
    }

    public String saveImage(MultipartFile file) {
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (ext == null || !ALLOWED.contains(ext.toLowerCase())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "File ảnh không hợp lệ",
                    Map.of("file", "Chỉ chấp nhận jpg, jpeg, png, gif, webp"));
        }
        String fileName = UUID.randomUUID() + "." + ext.toLowerCase();

        try {
            Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể lưu file ảnh", null);
        }
        return "/uploads/" + fileName;
    }
}
