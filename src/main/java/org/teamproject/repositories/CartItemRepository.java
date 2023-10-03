package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.teamproject.controllers.admin.dtos.CartListForm;
import org.teamproject.entities.Cart;
import org.teamproject.entities.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndBookNo(Long cartId, Long bookNo);

    List<CartListForm> findCartListForm(Long cartId);
}
