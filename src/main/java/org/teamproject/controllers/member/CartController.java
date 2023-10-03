package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.teamproject.models.book.CartService;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
}
