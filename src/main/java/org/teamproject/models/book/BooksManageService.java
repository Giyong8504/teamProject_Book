package org.teamproject.models.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Books;
import org.teamproject.repositories.BooksRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksManageService { // 등록, 불러오기, 수정, 삭제 서비스
    private final BooksRepository booksRepository;

    public Books saveBook(Books book) { // 새로운 상품을 DB에 저장
        // 새로운 상품 정보 생성
        Books newBook = Books.builder()
                .bookId(book.getBookId())
                .bookNm(book.getBookNm())
                .bookDesc(book.getBookDesc())
                .price(book.getPrice())
                .email(book.getEmail())
                .build();
        // 저장하고 반환
        return booksRepository.save(newBook);
    }

    public Books bookView(Long bookNo) { // 개별 상품 읽어들일 때 사용
        return booksRepository.findById(bookNo).get();
    }

    public List<Books> allBookView() { // 전체 상품 list
        return booksRepository.findAll();
    }
    //=> 수정 필요
    public void bookUpdate(Books book, Long bookNo) { // 상품 수정
        Books update = booksRepository.findById(bookNo).orElse(null);
        update.setBookId(book.getBookId());
        update.setBookNm(book.getBookNm());
        update.setBookDesc(book.getBookDesc());
        update.setPrice(book.getPrice());
    }

    public void bookDelete(Long bookNo) { // 상품 삭제
        booksRepository.deleteById(bookNo);
    }

}
