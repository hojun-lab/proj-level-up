package org.rojojun.levelupserver.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.common.BaseEntity;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Member extends BaseEntity {
    private String email;
    private String password;
    private String nickname;
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 사용자 권한 (예: ROLE_USER, ROLE_ADMIN)

    private String provider; // OAuth 제공자 (예: "google")
    private String providerId; // OAuth 제공자의 사용자 식별 ID


    public static Member of(String email, String nickname, String provider, String providerId) {
        return new Member(email, UUID.randomUUID().toString(), nickname, "", Role.ROLE_USER, provider, providerId);
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateName(String name) {
        this.nickname = name;
    }

    public Member updateRole(Role role) {
        this.role = role;
        return this;
    }


    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
}
