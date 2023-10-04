package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Books;
import org.teamproject.entities.product.Category;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository categoryRepository;
    private final BooksRepository booksRepository;

    public Books getBook(Integer bookNo) {
        return booksRepository.findById(bookNo).get();
    }

    public List<Category> getAll(String mode) {
        return categoryRepository.getAll(mode);
    }

}
