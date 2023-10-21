package org.teamproject.models.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Books;
import org.teamproject.entities.FileInfo;
import org.teamproject.entities.product.Category;
import org.teamproject.models.files.FileInfoService;
import org.teamproject.models.product.BookNotFoundException;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository categoryRepository;
    private final BooksRepository booksRepository;
    private final FileInfoService fileInfoService;

    /**
     * 카테고리 조회
     * @param cateCd
     * @return
     */
    public Category getCateCd(String cateCd) {
        return categoryRepository.findById(cateCd).orElse(null);
    }

    /**
     * 분류 전체 목록 조회
     * @param mode
     * @return
     */
    public List<Category> getAll(String mode) {
        return categoryRepository.getAll(mode);
    }

}
