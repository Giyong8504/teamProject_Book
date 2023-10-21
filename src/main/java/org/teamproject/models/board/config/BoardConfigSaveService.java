package org.teamproject.models.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.teamproject.commons.constants.Role;
import org.teamproject.controllers.admin.BoardForm;
import org.teamproject.entities.Board;
import org.teamproject.repositories.BoardRepository;

// 게시판 추가, 수정 설정
@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;

    public void save(BoardForm boardForm) {
        save(boardForm, null);
    } // Controller -> boardForm

    public void save(BoardForm boardForm, Errors errors) {

        if (errors != null && errors.hasErrors()) {
            return;
            // 에러가 NULL 값이 아니고 NULL 값이면
            // 실행하지 않기 위함
        }

        /**
         * 게시판 설정 조회 -> 없으면 중복 여부 체크 후 엔티티 생성
         * 게시판 등록모드인 경우는 중복여부 체크
         *
         */



        String bId = boardForm.getBId();
        Board board = boardRepository.findById(bId).orElseGet(Board::new);
        // 엔티티를 불러오는것
        // orElseGet ==  조회가 안될경우 대비

        String mode = boardForm.getMode();
        if ((mode == null || !mode.equals("update")) && board.getBId() != null) { // 게시판 등록 -> 중복 여부 체크
            throw new DuplicateBoardConfigException();
        }

        board.setBId(bId);
        board.setBName(boardForm.getBName());
        board.setUse(boardForm.isUse());
        board.setRowsOfPage(boardForm.getRowsOfPage());
        board.setShowViewList(boardForm.isShowViewList());
        board.setCategory(boardForm.getCategory());

        board.setListAccessRole(Role.valueOf(boardForm.getListAccessRole()));
        board.setViewAccessRole(Role.valueOf(boardForm.getViewAccessRole()));
        board.setWriteAccessRole(Role.valueOf(boardForm.getWriteAccessRole()));
        board.setReplyAccessRole(Role.valueOf(boardForm.getReplyAccessRole()));
        board.setCommentAccessRole(Role.valueOf(boardForm.getCommentAccessRole()));

        board.setUseEditor(boardForm.isUseEditor());
        board.setUseAttachFile(boardForm.isUseAttachFile());
        board.setUseAttachImage(boardForm.isUseAttachImage());

        board.setLocationAfterWriting(boardForm.getLocationAfterWriting());
        board.setUseReply(boardForm.isUseReply());
        board.setUseComment(boardForm.isUseComment());
        board.setSkin(boardForm.getSkin());
        System.out.println(board);
        boardRepository.saveAndFlush(board);;
    }
}
