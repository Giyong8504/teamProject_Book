package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.teamproject.commons.constants.OrdersStatus;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderItem extends BaseMemberEntity { // 주문 상품에 대한 정보
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long cartNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookNo")
    private Books book;

    private String cateNm;

    @Column(length = 100, nullable = false)
    private String bookNm;
    private int price;
    private int ea = 1;
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private OrdersStatus status = OrdersStatus.READY;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderNo")
    private OrderInfo orderInfo;
}
