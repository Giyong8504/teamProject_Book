package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CartInfo extends BaseEntity {
    @Id @GeneratedValue
    private Long cartNo;

    private int uid;

    @Column(length=10, nullable = false)
    private String mode = "direct"; // 바로 구매

    private int ea = 1; // 구매 수량 1로 초기화

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bookNo")
    private Books book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;
}
