package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.product.Category;

public interface CategoryRepository extends JpaRepository<Category, String>, QuerydslPredicateExecutor<Category> {
}
