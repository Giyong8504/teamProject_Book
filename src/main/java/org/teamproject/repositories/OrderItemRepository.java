package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, QuerydslPredicateExecutor<OrderItem> {
}
