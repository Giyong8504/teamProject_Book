package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.CartInfo;

public interface CartInfoRepository extends JpaRepository<CartInfo, Long>, QuerydslPredicateExecutor<CartInfo> {
}
