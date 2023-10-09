package org.teamproject.models.cart;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.Utils;
import org.teamproject.entities.Books;
import org.teamproject.entities.CartInfo;
import org.teamproject.entities.QCartInfo;
import org.teamproject.models.product.ProductInfoService;
import org.teamproject.repositories.CartInfoRepository;

import java.util.List;
import java.util.Objects;

@Service("cartInfoService2")
@RequiredArgsConstructor
public class CartInfoService {
    private final ProductInfoService productInfoService;
    private final CartInfoRepository repository;
    private final MemberUtil memberUtil;
    private final Utils utils;
    private final JPAQueryFactory factory;

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
            builder.and(cartInfo.uid.eq(uid))
                    .and(cartInfo.member.userNo.isNull());
        }

        return repository.findOne(builder).orElse(null);
    }

    public List<CartInfo> getList(String mode) {
        mode = Objects.requireNonNullElse(mode, "cart"); // 값이 없으면 카트로
        QCartInfo cartInfo = QCartInfo.cartInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cartInfo.mode.eq(mode));
        if (memberUtil.isLogin()) { // 회원일때
            builder.and(cartInfo.member.userNo.eq(memberUtil.getMember().getUserNo()));
        } else { // 비회원일때
            builder.and(cartInfo.uid.eq(utils.guestUid()))
                    .and(cartInfo.member.userNo.isNull()); // 회원정보 == null
        }

        PathBuilder pathBuilder = new PathBuilder(CartInfo.class, "cartInfo");

        List<CartInfo> items = factory.selectFrom(cartInfo)
                .leftJoin(cartInfo.member)
                .leftJoin(cartInfo.book)
                .fetchJoin()
                .where(builder)
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("createdAt")))
                .fetch();

        items.stream().forEach(this::addCartInfo);

        return items;
    }

    private void addCartInfo(CartInfo cartInfo) {
        Books book = cartInfo.getBook();
        if (book != null) productInfoService.addFileInfo(book);
    }
}
