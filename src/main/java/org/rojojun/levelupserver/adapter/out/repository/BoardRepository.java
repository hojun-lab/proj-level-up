package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
