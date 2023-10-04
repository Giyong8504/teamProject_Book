package org.teamproject.entities.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.entities.BaseEntity;


@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Orders extends BaseEntity {

    @Id @GeneratedValue
    @Column(length = 40, nullable = false)
    private Long id; // 주문 아이디

    @Column(length = 40, nullable = false)
    private String orderNo; // 주문번호

    @Column(length = 50, nullable = false)
    private String courier; // 택배사

    @Column(length = 40, nullable = false)
    private Long trackingNum; // 운송장번호

    private String releaseDt; // 출고 예정일

    private String productNm; // 등록상품명

    private String receiver; // 수취인 정보

    @Column(length = 70, nullable = false)
    private String destination; // 배송지

    @Column(length = 30, nullable = false)
    private String status; // 배송상태

    private String userInfo; // 주문자 정보
}
