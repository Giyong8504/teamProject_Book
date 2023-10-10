package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Cart extends BaseEntity {
    /* 장바구니 */
    @Id @GeneratedValue
    private Long cartNo;

    @Column(length = 40, nullable = false)
    private String buyerNm; // 주문자명

    private List<Long> bookNo; // 주문한 상품 번호

    private Long buyerCnt; // 주문한 상품 수량

    /* 사용자 연동 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo", nullable = false)
    private Member member; // 해당 구매자
}

