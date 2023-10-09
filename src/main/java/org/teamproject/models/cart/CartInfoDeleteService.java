package org.teamproject.models.cart;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.Utils;
import org.teamproject.entities.QCartInfo;
import org.teamproject.repositories.CartInfoRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartInfoDeleteService {
    private final CartInfoRepository repository;
    private final MemberUtil memberUtil;
    private final Utils utils;

    /**
     * 장바구니 비우기
     *
     * @param mode : direct - 바로구매 상품, cart - 장바구니 상품
     */

    public void deleteAll(String mode) {



    }
}
