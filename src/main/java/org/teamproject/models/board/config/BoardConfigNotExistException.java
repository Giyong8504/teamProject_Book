package org.teamproject.models.board.config;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class BoardConfigNotExistException extends CommonException {

    public BoardConfigNotExistException() {
        super(bundleValidation.getString("Validation.board.notExists"), HttpStatus.BAD_REQUEST);
    }

}
