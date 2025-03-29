package org.rojojun.levelupserver.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rojojun.levelupserver.adapter.out.projection.BoardProjection;
import org.rojojun.levelupserver.domain.board.entity.Board;
import org.rojojun.levelupserver.domain.board.entity.BoardStatus;
import org.rojojun.levelupserver.domain.board.entity.QBoard;
import org.rojojun.levelupserver.domain.board.entity.QVideo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.rojojun.levelupserver.domain.board.entity.QBoard.board;
import static org.rojojun.levelupserver.domain.board.entity.QReply.reply;
import static org.rojojun.levelupserver.domain.board.entity.QVideo.video;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Slice<BoardProjection> getBoardSlice(Pageable pageable) {
        List<BoardProjection> result = queryFactory.select(Projections.constructor(
                        BoardProjection.class,
                        board.writer.nickname,
                        board.writer.profilePicture,
                        video.url,
                        board.content,
                        board.createdAt,
                        reply.id.count()
                ))
                .from(board)
                .join(video).on(board.eq(video.board))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.boardStatus.eq(BoardStatus.USED))
                .groupBy(board.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = result.size() > pageable.getPageSize();
        if (hasNext) {
            result.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(result, pageable, hasNext);
    }
}
