package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>, QuerydslPredicateExecutor<OrderInfo> {
}
