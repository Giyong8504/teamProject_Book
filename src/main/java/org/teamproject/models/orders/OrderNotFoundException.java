package org.teamproject.models.orders;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class OrderNotFoundException extends CommonException {
    public OrderNotFoundException() {
        super(bundleValidation.getString("NotFound.orderInfo"), HttpStatus.NOT_FOUND);
    }
}
