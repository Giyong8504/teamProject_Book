package org.teamproject.models.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.AlertException;
import org.teamproject.commons.validators.RequiredValidator;
import org.teamproject.controllers.admin.dtos.CategoryForm;
import org.teamproject.entities.product.Category;
import org.teamproject.repositories.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryRegistService implements RequiredValidator {
    private final CategoryRepository categoryRepository;

    public void regist(CategoryForm form) {
        check(form); // 유효성 검사

        Category category = Category.builder()
                .cateCd(form.getCateCd())
                .cateNm(form.getCateNm())
                .build();

        categoryRepository.saveAndFlush(category);
    }

    private void check(CategoryForm form) {
        String cateCd = form.getCateCd();
        requiredCheck(cateCd, "분류 코드를 입력하세요.");
        requiredCheck(form.getCateNm(), "분류명을 입력하세요.");

        if (categoryRepository.exists(cateCd)) {
            throw new AlertException("이미 등록된 분류 코드 입니다.");
        }
    }

}
