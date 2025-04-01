package org.rojojun.levelupserver.adapter.out.dto;

public record TokenDto(
        String token,
        int expirationTime
) {
    public TokenDto(String token) {
        this(token, 3600);
    }
}

