package org.teamproject.models.cart;

import org.springframework.http.HttpStatus;
import org.teamproject.commons.CommonException;

public class CartItemNotFoundException extends CommonException {
    public CartItemNotFoundException() {
        super(bundleValidation.getString("NotFound.cartItem"), HttpStatus.BAD_REQUEST);
    }
}
