package org.rojojun.levelupserver.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("fine");
    }
}
