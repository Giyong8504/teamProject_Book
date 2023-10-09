package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.Books;

public interface BooksRepository extends JpaRepository<Books, Long>, QuerydslPredicateExecutor<Books> {
}
