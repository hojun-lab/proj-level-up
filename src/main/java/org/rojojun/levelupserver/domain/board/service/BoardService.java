package org.rojojun.levelupserver.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.projection.BoardProjection;
import org.rojojun.levelupserver.adapter.out.repository.BoardQueryRepository;
import org.rojojun.levelupserver.adapter.out.repository.BoardRepository;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.BoardStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow();
    }

    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> findAllBy(String nickname) {
        return boardRepository.findAllByWriter_NicknameAndBoardStatus(nickname, BoardStatus.USED)
                .orElse(new ArrayList<>());
    }

    public Slice<BoardProjection> getAll(Pageable pageable) {
        return boardQueryRepository.getBoardSlice(pageable);
    }
}
