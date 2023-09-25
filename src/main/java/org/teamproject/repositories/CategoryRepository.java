package org.teamproject.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.product.Category;
import org.teamproject.entities.product.QCategory;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

public interface CategoryRepository extends JpaRepository<Category, String>, QuerydslPredicateExecutor<Category> {

    default boolean exists(String cateCd) {
        return exists(QCategory.category.cateCd.eq(cateCd));
    }

    default List<Category> getAll(String mode) {
        QCategory category = QCategory.category;
        BooleanBuilder builder = new BooleanBuilder();
        if (mode == null || !mode.equals("all")) {
                builder.and(category.open.eq(true));
        }

        return (List<Category>) findAll(builder, Sort.by(desc("listOrder"), desc("regDt")));
    }
}
