package org.rojojun.levelupserver.common;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileConfig {
    private final String URL = "/upload";
    private Path uploadpath;

    public String uploadFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        Path targetLocation = this.uploadpath.resolve(originalFileName);
        try {
            file.transferTo(targetLocation);
            return targetLocation.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() {
        try {
            this.uploadpath = Paths.get(URL).toAbsolutePath().normalize();
            Files.createDirectories(uploadpath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
