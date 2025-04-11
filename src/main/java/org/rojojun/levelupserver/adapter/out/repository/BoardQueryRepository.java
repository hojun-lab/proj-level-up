package org.rojojun.levelupserver.adapter.out.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rojojun.levelupserver.adapter.out.projection.BoardProjection;
import org.rojojun.levelupserver.domain.board.entity.BoardStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.rojojun.levelupserver.domain.board.entity.QBoard.board;
import static org.rojojun.levelupserver.domain.board.entity.QReply.reply;
import static org.rojojun.levelupserver.domain.board.entity.QVideo.video;
import static org.rojojun.levelupserver.domain.member.entity.QMember.member;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Slice<BoardProjection> getBoardSlice(Pageable pageable) {
        List<BoardProjection> result = queryFactory.select(Projections.constructor(
                        BoardProjection.class,
                        board.id,
                        member.nickname,
                        member.profilePicture,
                        video.videoUrl,
                        board.content,
                        board.createdAt,
                        Expressions.asNumber(reply.id.count()).coalesce(0L)
                ))
                .from(board)
                .join(member).on(board.writer.eq(member))
                .join(video).on(video.board.eq(board))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.boardStatus.eq(BoardStatus.USED))
                .groupBy(board.id)
                .orderBy(board.createdAt.desc())
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
