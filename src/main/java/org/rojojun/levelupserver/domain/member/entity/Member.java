package org.rojojun.levelupserver.domain.member.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Member extends BaseEntity {
    private String email;
    private String password;
    private String nickname;

    public static Member of(String email, String password, String nickname) {
        return new Member(email, password, nickname);
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }
}
