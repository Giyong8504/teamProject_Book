package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.product.Category;
import org.teamproject.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll(String mode) {
        return categoryRepository.getAll(mode);
    }

}
