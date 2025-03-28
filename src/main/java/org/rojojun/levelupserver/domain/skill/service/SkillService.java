package org.rojojun.levelupserver.domain.skill.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.SkillRepository;
import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public boolean isSkillExist(String name) {
        return skillRepository.existsByName(name);
    }

    public Optional<Skill> findSkillByOr(String name) {
        return skillRepository.findByName(name);
    }
}
