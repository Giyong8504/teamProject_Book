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
    @Id @GeneratedValue
    private int id;

    @OneToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn(name="email")
    private Member member; // 해당 구매자

    private int count; // 카트에 담긴 총 상품 개수

    @OneToMany(mappedBy = "cart") // 장바구니와 장바구니 상품들
    private List<CartItem> cartItem = new ArrayList<>();

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setCount(0);
        cart.setMember(member);
        return cart;
    }
}

