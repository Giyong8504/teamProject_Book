package org.teamproject.models.board.config;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class BoardNotAllowAccessException extends CommonException {

    public BoardNotAllowAccessException() {
        super(bundleValidation.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
