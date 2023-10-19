package org.teamproject.models.orders;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.Utils;
import org.teamproject.controllers.orders.CartForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.CartInfo;
import org.teamproject.models.product.ProductInfoService;
import org.teamproject.repositories.CartInfoRepository;

@Service
@RequiredArgsConstructor
public class CartInfoSaveService {
    private final ProductInfoService productInfoService;
    private final CartInfoService cartInfoService;
    private final CartInfoDeleteService cartInfoDeleteService;
    private final MemberUtil memberUtil;
    private final CartInfoRepository repository;
    private final Utils utils;

    public void save(CartForm form) {
        Long bookNo = form.getBookNo();
        String mode = form.getMode();

       /* 바로 구매 -> 기존 바로구매 정보 CartInfo에서 삭제 */
        if (mode.equals("direct")) {
            cartInfoDeleteService.deleteAll("direct");
        }

        Books book = productInfoService.get(bookNo);
        CartInfo cart = mode.equals("direct") ? null : cartInfoService.getByBookNo(bookNo);
        if (cart == null) { // 신규 추가 - 장바구니에 없는 경우 + 바로 구매(direct)
            cart = new ModelMapper().map(form, CartInfo.class);
            cart.setBook(book);
            cart.setUid(utils.guestUid()); // 회원 정보 가져올 수 있게 회원 정보 추가

            if (memberUtil.isLogin()) { // 로그인 상태인 경우 회원 엔티티 추가
                cart.setMember(memberUtil.getEntity());
            }
        } else { // 수량 변경 - 장바구니
            cart.setEa(cart.getEa() + form.getEa());
        }

        repository.saveAndFlush(cart);


    }
}
