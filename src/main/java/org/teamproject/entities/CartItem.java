package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.controllers.admin.dtos.CartListForm;

import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id") // Cart 기본키 id -> 외래키명 cart_id
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_no")
    private Books book;

    private int count; // 카트에 담긴 상품 개수

    public static CartItem createCartItem(Cart cart, Books book, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setCount(count);
        return cartItem;
    }

    // 이미 담겨있는 무건 또 담을 경우 수량 증가
    public void addCount(int count) { // 이미 담겨있는 물건 또 담을 경우 수량 증가
        this.count += count;
    }
}
