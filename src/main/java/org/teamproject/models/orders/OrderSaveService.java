package org.teamproject.models.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamproject.commons.MemberUtil;
import org.teamproject.commons.constants.OrdersStatus;
import org.teamproject.commons.constants.PaymentType;
import org.teamproject.controllers.orders.OrderForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.CartInfo;
import org.teamproject.entities.OrderInfo;
import org.teamproject.entities.OrderItem;
import org.teamproject.entities.product.Category;
import org.teamproject.repositories.OrderInfoRepository;
import org.teamproject.repositories.OrderItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderSaveService {
    private final CartInfoService cartInfoService;
    private final OrderInfoRepository infoRepository;
    private final OrderItemRepository itemRepository;
    private final MemberUtil memberUtil;

    public void save(OrderForm form) {
        List<Long> cartNos = form.getCartNo();
        List<CartInfo> cartItems = cartInfoService.getList(cartNos);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CartItemNotFoundException();
        }

        int payPrice = cartInfoService.getTotalPrice(cartItems);

        /* 주문서 정보 저장 S */
        OrderInfo orderInfo = OrderInfo.builder()
                .orderName(form.getOrderName())
                .orderEmail(form.getOrderEmail())
                .orderMobile(form.getOrderMobile())
                .receiverName(form.getReceiverName())
                .receiverMobile(form.getReceiverMobile())
                .address(form.getAddress())
                .zonecode(form.getZonecode())
                .addressSub(form.getAddressSub())
                .paymentType(PaymentType.valueOf(form.getPaymentType()))
                .payPrice(payPrice)
                .member(memberUtil.getEntity())
                .build();

        infoRepository.saveAndFlush(orderInfo);
        /* 주문서 정보 저장 E */

        /* 주문 상품 정보 저장 S */
        List<OrderItem> items = new ArrayList<>();
        for (CartInfo cartItem : cartItems) {
            Books book = cartItem.getBook();
            Category category = book.getCategory();
            OrderItem item = OrderItem.builder()
                    .status(OrdersStatus.READY)
                    .ea(cartItem.getEa())
                    .book(book)
                    .price(book.getPrice())
                    .cateNm(category == null ? null : category.getCateNm())
                    .totalPrice(cartItem.getTotalPrice())
                    .cartNo(cartItem.getCartNo())
                    .orderInfo(orderInfo)
                    .build();
            items.add(item);
        }

        itemRepository.saveAllAndFlush(items);
        /* 주문 상품 정보 저장 E */
    }
}
