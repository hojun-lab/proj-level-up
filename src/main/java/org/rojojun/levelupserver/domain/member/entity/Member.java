package org.rojojun.levelupserver.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.adapter.out.dto.enums.UserLevel;
import org.rojojun.levelupserver.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Member extends BaseEntity {
    private String email;
    private String password;
    private String nickname;
    private String profilePicture;

    public static Member of(String email, String password, String nickname) {
        return new Member(email, password, nickname, null);
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }
}
