package org.rojojun.levelupserver.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.BoardRepository;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow();
    }

    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }
}
