package org.teamproject.models.book;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamproject.controllers.admin.dtos.CartItemForm;
import org.teamproject.controllers.admin.dtos.CartListForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.Cart;
import org.teamproject.entities.CartItem;
import org.teamproject.entities.Member;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.CartItemRepository;
import org.teamproject.repositories.CartRepository;
import org.teamproject.repositories.MemberRepository;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    /* 장바구니 조회 */
    @Transactional(readOnly = true) // DB에서 데이터 수정 작업 허용 X
    public List<CartListForm> getCartList(String email) {
        List<CartListForm> cartListForms = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findById(member.getUserNo()).orElse(null);

        if (cart == null) {
            return cartListForms;
        }

        cartListForms = cartItemRepository.findCartListForm(cart.getId());
        return cartListForms;
    }

    /* 현재 로그인한 사용자가 장바구니 주인인지 확인 */
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        // 현재 로그인된 사용자
        Member logMember = memberRepository.findByEmail(email);

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedmember = cartItem.getCart().getMember();

        if (StringUtils.equals(logMember.getEmail(), savedmember.getEmail())) {
            return true;
        }
        return false;
    }

    /* 장바구니 상품 수량 변경 */

}
