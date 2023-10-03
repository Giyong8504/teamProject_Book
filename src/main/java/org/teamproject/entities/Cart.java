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
    private Long id;

    @OneToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn(name="email")
    private Member member; // 해당 구매자

//    @OneToMany(mappedBy = "cart") // 장바구니와 장바구니 상품들
//    private List<CartItem> cartItem = new ArrayList<>();

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}

