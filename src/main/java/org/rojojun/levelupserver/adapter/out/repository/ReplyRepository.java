package org.rojojun.levelupserver.adapter.out.repository;

import org.rojojun.levelupserver.adapter.out.dto.ReplyDto;
import org.rojojun.levelupserver.domain.board.entity.BoardStatus;
import org.rojojun.levelupserver.domain.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<List<Reply>> findAllByBoard_IdAndStatus(Long boardId, BoardStatus boardStatus);
}
