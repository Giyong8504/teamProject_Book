package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.BoardView;
import org.teamproject.entities.BoardViewId;
import org.teamproject.entities.QBoard;
import org.teamproject.entities.QBoardView;

public interface BoardViewRepository extends JpaRepository<BoardView,BoardViewId>, QuerydslPredicateExecutor {
/**
 * 게시글별 조회수 조회
 *
 *
 */
    default long getHit(Long id){
        QBoardView boardView = QBoardView.boardView;
       return count(boardView.id.eq(id));
        // 게시글 아이디별로 카운트
    }

}
