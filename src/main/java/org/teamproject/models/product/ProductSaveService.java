package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.Utils;
import org.teamproject.commons.constants.BookStatus;
import org.teamproject.commons.validators.RequiredValidator;
import org.teamproject.controllers.admin.ProductForm;
import org.teamproject.entities.Books;
import org.teamproject.models.categories.CategoryInfoService;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.FileInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSaveService implements RequiredValidator {
    private final BooksRepository booksRepository;
    private final CategoryInfoService categoryInfoService;
    private final FileInfoRepository fileInfoRepository;
    private final Utils utils;


    public void save(ProductForm form) {
        String gid = form.getGid();
        Long bookNo = form.getBookNo();
        Books books = null;
        if (bookNo != null) {
            books = booksRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        } else {
            books = new Books();
            books.setGid(gid); // 처음 추가할 때만 저장
            System.out.println("bookNo1 = " + bookNo);
        }

        books.setCategory(categoryInfoService.getCateCd(form.getCateCd()));
        books.setBookNm(form.getProductNm());
        books.setAuthor(form.getAuthor());
        books.setPublisher(form.getPublisher());
        books.setPrice(form.getPrice());
        books.setStock(form.getStock());
        books.setDescription(form.getDescription());
        books.setStatus(BookStatus.valueOf(form.getStatus()));

        booksRepository.saveAndFlush(books);
        form.setBookNo(books.getBookNo());

        /* 파일 업로드 */
        fileInfoRepository.processDone(gid);
    }

    /* 목록 저장 */
    public void saveList(ProductForm form) {
        List<Integer> chks = form.getChkNo();
        nullCheck(chks, utils.getMessage("NotSelected.edit", "validation"));

        for (Integer chk : chks) {
            String bookNo = utils.getParam("bookNo_" + chk);
            Long bookNoLong = Long.parseLong(bookNo);
            Books item = booksRepository.findById(bookNoLong).orElse(null);
            if (item == null) continue;

            item.setStatus(BookStatus.valueOf(utils.getParam("status_" + chk)));
            item.setStock(Integer.parseInt(utils.getParam("stock_" + chk)));
            item.setListOrder(Long.parseLong(utils.getParam("listOrder_" + chk)));
        }
        booksRepository.flush();
    }
}
