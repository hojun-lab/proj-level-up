package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByBoard_Id(Long id);
    boolean existsByVideoUrl(String url);
    void deleteByBoard(Board board);
}
