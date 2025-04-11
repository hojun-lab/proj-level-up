package org.rojojun.levelupserver.port;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.common.FileConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FilePort {
    private final FileConfig fileConfig;

    public String upload(MultipartFile multipartFile) {
        return fileConfig.uploadFile(multipartFile);
    }
}
