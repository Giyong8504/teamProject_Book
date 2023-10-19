package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.commons.constants.PaymentType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderInfo extends BaseMemberEntity{ // 주문자에 대한 정보

    @Id @GeneratedValue
    private Long id;

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
    private PaymentType paymentType = PaymentType.LBT;

    private int payPrice; // 결제 금액

    @Column(length=50)
    private String deliveryCompany; // 배송 업체
    @Column(length=50)
    private String invoice; // 운송장 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo") // 로그인 했을때만 / 로그인하지 않았을 때는 null
    private Member member;

    @Transient
    private String bookNm; // 내부에서 사용

    @OneToMany(mappedBy = "orderInfo", fetch=FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();
}
