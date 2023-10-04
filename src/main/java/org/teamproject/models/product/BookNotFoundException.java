package org.teamproject.models.product;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class BookNotFoundException extends CommonException {
    public BookNotFoundException() {
        super(bundleValidation.getString("NotFound.book"), HttpStatus.BAD_REQUEST);
    }
}
