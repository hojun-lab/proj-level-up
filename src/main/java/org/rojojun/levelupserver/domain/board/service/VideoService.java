package org.rojojun.levelupserver.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.rojojun.levelupserver.adapter.out.repository.VideoRepository;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.Video;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public Video getVideoBy(Long boardId) {
        return videoRepository.findByBoard_Id(boardId)
                .orElseThrow();
    }

    public void saveVideo(Video video) {
        videoRepository.save(video);
    }

    public boolean existBy(String url) {
        return videoRepository.existsByUrl(url);
    }

    public void deleteBy(Board board) {
        videoRepository.deleteByBoard(board);
    }
}
