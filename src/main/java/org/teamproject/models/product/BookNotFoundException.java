package org.teamproject.models.product;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.AlertException;
import org.teamproject.commons.CommonException;

public class BookNotFoundException extends AlertException {
    public BookNotFoundException() {
        super(bundleValidation.getString("NotFound.book"));
    }
}
