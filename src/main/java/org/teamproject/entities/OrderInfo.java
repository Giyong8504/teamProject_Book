package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.commons.constants.PaymentType;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderInfo extends BaseMemberEntity{ // 주문자에 대한 정보
    @Id @GeneratedValue
    private Long orderNo;

    @Column(length = 40, nullable = false)
    private String orderName;

    @Column(length = 100, nullable = false)
    private String orderEmail;

    @Column(length = 11, nullable = false)
    private String orderMobile;

    @Column(length = 40, nullable = false)
    private String receiverName;

    @Column(length = 11, nullable = false)
    private String receiverMobile;

    @Column(length = 10, nullable = false)
    private String zonecode;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100, nullable = false)
    private String addressSub;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private PaymentType paymentType;

    private int payPrice; // 결제 금액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo") // 로그인 했을때만 / 로그인하지 않았을 때는 null
    private Member member;
}
