package org.rojojun.levelupserver.adapter.in.api;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.SkillDetailDto;
import org.rojojun.levelupserver.adapter.out.dto.SkillResponseDto;
import org.rojojun.levelupserver.port.SkillPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SkillController {
    private final SkillPort skillPort;

    @GetMapping("/skill")
    public ResponseEntity<List<SkillResponseDto>> findAllBy(Principal principal) {
        List<SkillResponseDto> result = skillPort.findAllBy(principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<SkillDetailDto> findById(Principal principal, Long skillId) {
        SkillDetailDto result = skillPort.findById(principal.getName(), skillId);
        return ResponseEntity.ok(result);
    }
}
