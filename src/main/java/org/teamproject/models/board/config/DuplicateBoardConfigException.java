package org.teamproject.models.board.config;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class DuplicateBoardConfigException extends CommonException {

    public DuplicateBoardConfigException() {
        super("이미 등록된 게시판입니다.", HttpStatus.BAD_REQUEST);
    }
}
