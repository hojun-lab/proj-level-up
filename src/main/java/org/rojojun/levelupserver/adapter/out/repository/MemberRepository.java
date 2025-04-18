package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByNickname(String nickname);
}
