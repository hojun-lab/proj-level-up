package org.rojojun.levelupserver.adapter.out.dto;

import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;

public record UserInfoResponseDto(
        String nickname,
        UserLevel userLevel
) {
}
