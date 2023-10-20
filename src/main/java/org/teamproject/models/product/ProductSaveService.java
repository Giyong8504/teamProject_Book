package org.teamproject.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.commons.constants.BookStatus;
import org.teamproject.controllers.admin.dtos.ProductForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.product.Category;
import org.teamproject.models.categories.CategoryInfoService;
import org.teamproject.repositories.BooksRepository;
import org.teamproject.repositories.FileInfoRepository;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProductSaveService {

    private final BooksRepository booksRepository;
    private final CategoryInfoService categoryInfoService;
    private final FileInfoRepository fileInfoRepository;

    public void save(ProductForm form) {
        String mode = Objects.requireNonNullElse(form.getMode(), "edit");
        Long bookNo = form.getBookNo();
        Books books = null;
        if (bookNo != null) {
            books = booksRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        } else {
            books = new Books();
            books.setGid(form.getGid()); // 처음 추가할 때만 저장
        }
        String cateCd = form.getCateCd();
        if (cateCd != null) {
            Category category = categoryInfoService.getCateCd(cateCd);
            books.setCategory(category);
        }

        books.setBookNm(form.getBookNm());
        books.setAuthor(form.getAuthor());
        books.setPublisher(form.getPublisher());
        books.setPrice(form.getPrice());
        books.setStock(form.getStock());
        books.setDescription(form.getDescription());
        books.setOrigin(form.getOrigin());
        books.setPublicationDt(form.getPublicationDt());
        books.setIsbn(form.getIsbn());
        books.setStatus(BookStatus.valueOf(form.getStatus()));

        booksRepository.saveAndFlush(books);

        /* 파일 업로드 */
        fileInfoRepository.processDone(form.getGid());
    }
}
