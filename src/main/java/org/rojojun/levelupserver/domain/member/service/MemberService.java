package org.rojojun.levelupserver.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.MemberRepository;
import org.rojojun.levelupserver.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findMemberBy(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public Optional<Member> findMemberBy(String nickname) {
        return memberRepository.findMemberByNickname(nickname);
    }
}
