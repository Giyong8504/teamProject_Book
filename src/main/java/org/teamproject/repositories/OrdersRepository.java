package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.product.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Orders> {
}
