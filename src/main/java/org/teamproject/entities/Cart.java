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
public class Cart {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn(name="email")
    Member member; // 해당 구매자

    @OneToMany(mappedBy = "cart") // 장바구니와 장바구니 상품들
    private List<CartItem> cartItem = new ArrayList<>();

    private int count; // 카트에 담긴 상품 개수
}
