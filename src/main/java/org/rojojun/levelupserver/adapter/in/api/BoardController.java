package org.rojojun.levelupserver.adapter.in.api;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.in.dto.BoardRequestDto;
import org.rojojun.levelupserver.adapter.out.dto.BoardDetailDto;
import org.rojojun.levelupserver.adapter.out.projection.BoardProjection;
import org.rojojun.levelupserver.port.BoardPort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardPort boardPort;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardDetailDto> getBoard(@PathVariable Long boardId) {
        BoardDetailDto result = boardPort.getBoardBy(boardId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/board")
    public ResponseEntity<Slice<BoardProjection>> getAll(Pageable pageable) {
        Slice<BoardProjection> result = boardPort.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/board")
    public ResponseEntity<Long> save(BoardRequestDto requestDto, Principal principal) {
        Long result = boardPort.saveBoard(requestDto, principal.getName());
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity<Long> modify(@PathVariable Long boardId, BoardRequestDto requestDto, Principal principal) {
        Long result = boardPort.modifyBoard(boardId, principal.getName(), requestDto);
        return ResponseEntity.ok(result);
    }
}
