package org.rojojun.levelupserver.adapter.in.api;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.MyPageResponseDto;
import org.rojojun.levelupserver.adapter.out.dto.UserInfoResponseDto;
import org.rojojun.levelupserver.port.MemberPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberPort memberPort;

    @PatchMapping("/my")
    public ResponseEntity<Void> modify(Principal principal, @RequestBody Map<String, String> requestBody) {
        String nickname = requestBody.get("nickname");
        memberPort.modifyNickname(principal.getName(), nickname);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<MyPageResponseDto> getMyPageInfo(Principal principal) {
        MyPageResponseDto result = memberPort.getMyPageInfo(principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/member/{userId}")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(Long userId) {
        UserInfoResponseDto result = memberPort.getUserInfo(userId);
        return ResponseEntity.ok(result);
    }
}
