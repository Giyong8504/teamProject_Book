package org.teamproject.jpaex;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.teamproject.entities.Book;
import org.teamproject.repositories.BookRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookTest1 {

    @MockBean
    private BookRepository bookRepository;


    @BeforeEach
    void init() {
        Book book = Book.builder()
                .bookId("ㄱ1234")
                .bookNm("고대문명")
                .price(10000L)
                .build();
        bookRepository.saveAndFlush(book);

    }
    @Test
    void test1() {
        Book book = bookRepository.findById("ㄱ1234");
        System.out.println(book);
    }
}
