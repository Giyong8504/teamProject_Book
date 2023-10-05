package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>, QuerydslPredicateExecutor<Cart> {
}
