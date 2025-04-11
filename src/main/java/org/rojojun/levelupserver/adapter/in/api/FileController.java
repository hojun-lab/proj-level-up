package org.rojojun.levelupserver.adapter.in.api;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.port.FilePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileController {
    private final FilePort filePort;

    @PostMapping("/upload")
        public ResponseEntity<String> upload(@RequestParam("files") MultipartFile multipartFile) {
        String result = filePort.upload(multipartFile);
        return ResponseEntity.ok(result);
    }
}
