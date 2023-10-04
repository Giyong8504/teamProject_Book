package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamproject.entities.Cart;
import org.teamproject.entities.Member;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
