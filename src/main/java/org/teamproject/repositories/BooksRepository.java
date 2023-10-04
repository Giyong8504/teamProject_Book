package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamproject.entities.Books;

public interface BooksRepository extends JpaRepository<Books, Integer> {
}
