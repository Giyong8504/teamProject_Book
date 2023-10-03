package org.teamproject.models.book;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamproject.controllers.admin.dtos.CartItemForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.Cart;
import org.teamproject.entities.CartItem;
import org.teamproject.entities.Member;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.CartItemRepository;
import org.teamproject.repositories.CartRepository;
import org.teamproject.repositories.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final BooksRepository booksRepository;


    /* 장바구니 담기 */
    public Long addCart(CartItemForm cartItemForm, String email) {
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findById(member.getUserNo()).orElse(null);

        /* 장바구니가 존재하지 않는다면 생성 */
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Books book = booksRepository.findById(cartItemForm.getBookId()).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findByCartIdAndBookNo(cart.getId(), book.getBookNo());

        /* 해당 상품이 장바구니에 없다면 생성 후 추가 */
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, book, cartItemForm.getCount());
            cartItemRepository.save(cartItem);
        } else { /* 해당 상품이 장바구니에 이미 존재하면 수량 증가 */
            cartItem.duplicateCount(cartItemForm.getCount());
        }
        return cartItem.getId();
    }

}
