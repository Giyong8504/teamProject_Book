package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.Utils;
import org.teamproject.controllers.admin.dtos.ProductForm;
import org.teamproject.entities.Books;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.commons.validators.RequiredValidator;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDeleteService implements RequiredValidator {
    private final BooksRepository repository;
    private final Utils utils;

    /**
     * 도서 삭제
     * @param bookNo
     */
    private void delete(Long bookNo) {
        Books item = repository.findById(bookNo).orElse(null);
        if (item != null) {
            repository.delete(item);
            repository.flush();
        }
    }
    /**
     * 목록 삭제
     */
    /*
    public void deleteList(ProductForm form) {
        List<Books> items = new ArrayList<>();
        List<Integer> checks = form.getChkNo();
        nullCheck(checks, utils.getMessage("NotSelected.delete", "validation"));

        for (Integer check : checks) {
            String bookNo = utils.getParam("bookNo_" + check);
            Long bookNoLong = Long.parseLong(bookNo);
            Books item = repository.findById(bookNoLong).orElse(null);
            if (item == null) continue;

            items.add(item);
        }
        repository.deleteAll();
        repository.flush();
    }
    */
}
