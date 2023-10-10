package org.teamproject.models.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.AlertException;
import org.teamproject.commons.Utils;
import org.teamproject.controllers.admin.dtos.CategoryForm;
import org.teamproject.entities.product.Category;
import org.teamproject.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryUpdateService {
    private final CategoryRepository categoryRepository;
    private final Utils utils;

    public void update(CategoryForm form) {
        List<Integer> chkNo = form.getChkNo();
        List<String> cateCds = form.getCateCds();
        if (chkNo == null || chkNo.isEmpty()) {
            throw new AlertException("수정할 분류를 선택하세요.");
        }

        for (int no : chkNo) {
            String cateCd = cateCds.get(no);
            Category category = categoryRepository.findById(cateCd).orElse(null);
            if (category == null) continue;
            category.setCateNm(utils.getParam("cateNm_" + no));
            category.setOpen(Boolean.parseBoolean(utils.getParam("open_" + no)));
            category.setListOrder(Long.parseLong(utils.getParam("listOrder_" + no)));
        }

        categoryRepository.flush();
    }

}
