package org.teamproject.controllers.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.teamproject.entities.Books;
import org.teamproject.models.book.BooksManageService;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BooksManageService bookManageService;

    /* 상품 등록 페이지 */
    @GetMapping("/member/book/new")
    public String bookSaveForm() {

        return "front/member/book"; // 책 등록 페이지 만들기
    }

    @PostMapping("/member/book/new/pro")
    public String bookSave(Books book) {
        bookManageService.saveBook(book);
        return "front/member/book";
    }

    /*

        이후 수정, 삭제도 추가하겠습니다.
     */

}