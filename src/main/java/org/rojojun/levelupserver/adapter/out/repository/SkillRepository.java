package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
    Optional<Skill> findByName(String name);
}
