package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamproject.entities.Cart;

public interface CartItemRepository extends JpaRepository<Cart, Long> {
}
