package org.teamproject.models.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.constants.Role;
import org.teamproject.entities.Board;
import org.teamproject.repositories.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

    public Board get(String bId, String location) { // 프론트 접근 권한 체크
        return get(bId, false, location);

        /**
         * 게시판 설정 조회
         * @param bId
         * @param isAdmin : true - 권한 체크 x
         *                : false - 권한 체크, location으로 목록, 보기, 글쓰기, 답글, 댓글
         * @param location : 기능 위치(list, view, write, reply, comment)
         *
         * @return
         */
    }

    public Board get(String bId, boolean isAdmin, String location) {

        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if (!isAdmin) { // 권한 체크
            accessCheck(board, location);
        }

        return board;
    }

    public Board get(String bId, boolean isAdmin) {
        return get(bId, isAdmin, null);
    }

    /**
     * 접근 권한 체크
     * @param board
     */
    private void accessCheck(Board board, String location) {

        Role role = Role.ALL;

        if(location.equals("list)")) { // 목록 접근 권한
            role = board.getListAccessRole();

        } else if (location.equals("view")) { // 게시글 접근 권한
            role = board.getViewAccessRole();

        } else if (location.equals("write")) { // 글쓰기 권한
            role = board.getWriteAccessRole();

            /** 비회원 게시글 여부 */
            if (!memberUtil.isLogin()) board.setGuest(true);

        } else if (location.equals("reply")) { // 답글 권한
            role = board.getReplyAccessRole();

        } else if (location.equals("comment")) { // 댓글 권한
            role = board.getCommentAccessRole();
        }

        if ((role == Role.USER && !memberUtil.isLogin()) || (role == Role.ADMIN && !memberUtil.isAdmin())) {
            throw new BoardNotAllowAccessException();
            // 사용자 로그인 화면이 아니거나
            // 관리자가 아닌경우
        }
    }
}
