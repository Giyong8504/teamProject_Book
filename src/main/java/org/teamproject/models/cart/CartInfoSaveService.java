package org.teamproject.models.cart;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.Utils;
import org.teamproject.controllers.member.cart.CartForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.CartInfo;
import org.teamproject.entities.Member;
import org.teamproject.models.product.ProductInfoService;
import org.teamproject.repositories.CartInfoRepository;

@Service
@RequiredArgsConstructor
public class CartInfoSaveService {
    private final ProductInfoService productInfoService;
    private final CartInfoService cartInfoService;
    private final MemberUtil memberUtil;
    private final CartInfoRepository repository;

    public void save(CartForm form) {
        Long bookNo = form.getBookNo();
        Books book = productInfoService.get(bookNo);
        CartInfo cart = cartInfoService.getByBookNo(bookNo);
        if (cart == null) { // 신규 추가 - 장바구니에 없는 경우 + 바로 구매(direct)
            cart = new ModelMapper().map(form, CartInfo.class);
            cart.setBook(book);
            if (memberUtil.isLogin()) { // 로그인 상태인 경우 회원 엔티티 추가
                cart.setMember(memberUtil.getEntity());
            }
        } else { // 수량 변경 - 장바구니
            cart.setEa(cart.getEa() + form.getEa());
        }

        repository.saveAndFlush(cart);


    }
}
