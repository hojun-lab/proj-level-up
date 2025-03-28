package org.rojojun.levelupserver.domain.board;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rojojun.levelupserver.common.BaseEntity;
import org.rojojun.levelupserver.domain.member.entity.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Reply extends BaseEntity {
    private String content;
}
