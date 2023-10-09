package org.teamproject.models.cart;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.Utils;
import org.teamproject.entities.CartInfo;
import org.teamproject.entities.QCartInfo;
import org.teamproject.repositories.CartInfoRepository;

@Service("cartInfoService2")
@RequiredArgsConstructor
public class CartInfoService {
    private final CartInfoRepository repository;
    private final MemberUtil memberUtil;
    private final Utils utils;

    // 책 번호 가져와서 조회
    public CartInfo getByBookNo(Long bookNo) {
        int uid = utils.guestUid();
        QCartInfo cartInfo = QCartInfo.cartInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cartInfo.book.bookNo.eq(bookNo));
        builder.and(cartInfo.mode.eq("cart")); // 장바구니일때는 수량만 추가

        if (memberUtil.isLogin()) {
            Long userNo = memberUtil.getMember().getUserNo();
            builder.and(cartInfo.member.userNo.eq(userNo));
        } else {
            builder.and(cartInfo.uid.eq(uid));
        }

        return repository.findOne(builder).orElse(null);
    }
}
