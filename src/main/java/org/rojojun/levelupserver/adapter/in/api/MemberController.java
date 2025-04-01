package org.rojojun.levelupserver.adapter.in.api;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.rojojun.levelupserver.adapter.in.dto.LoginDto;
import org.rojojun.levelupserver.adapter.in.dto.ReplyRequestDto;
import org.rojojun.levelupserver.adapter.in.dto.SignUpDto;
import org.rojojun.levelupserver.adapter.out.dto.MyPageResponseDto;
import org.rojojun.levelupserver.adapter.out.dto.TokenDto;
import org.rojojun.levelupserver.adapter.out.dto.UserInfoResponseDto;
import org.rojojun.levelupserver.port.MemberPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberPort memberPort;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<Long> signUp(@RequestBody SignUpDto signUpDto) {
        Long result = memberPort.signUp(signUpDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto requestDto) {
        TokenDto result = memberPort.login(requestDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/auth/id-check")
    public ResponseEntity<Boolean> idCheck(@RequestBody Map<String, String> requestMap) {
        if (requestMap.get("nickname") == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = memberPort.idCheck(requestMap.get("nickname"));
        return ResponseEntity.ok(result);
    }

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
