package org.teamproject.models.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.teamproject.commons.ListData;
import org.teamproject.commons.Pagination;
import org.teamproject.commons.constants.BookStatus;
import org.teamproject.controllers.admin.dtos.ProductForm;
import org.teamproject.entities.Books;
import org.teamproject.entities.FileInfo;
import org.teamproject.entities.QBooks;
import org.teamproject.entities.product.Category;
import org.teamproject.models.files.FileInfoService;
import org.teamproject.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final BooksRepository booksRepository;
    private final FileInfoService fileInfoService;
    private final EntityManager em;
    private final HttpServletRequest request;



    /**
     * 책 상품 개별조회
     * @param bookNo
     * @return
     */
    public Books get(Long bookNo) {
        Books book = booksRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        addFileInfo(book);

        String gid = book.getGid();
        book.setMainImages(fileInfoService.getListDone(gid, "main"));
        book.setListImages(fileInfoService.getListDone(gid, "list"));
        book.setEditorImages(fileInfoService.getListDone(gid, "editor"));

        return book;
    }

    public ProductForm getFormData(Long bookNo) {
        Books book = get(bookNo);

        ProductForm form = new ModelMapper().map(book, ProductForm.class);
        form.setStatus(book.getStatus().name());
        Category category = book.getCategory();
        if (category != null) {
            form.setCateCd(category.getCateCd());
        }
        return form;
    }


    /**
     * 도서 목록 조회
     * @param search
     * @return
     */
    public ListData<Books> getList(ProductSearch search) {
        QBooks books = QBooks.books;
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int page = search.getPage();
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * limit;


        /* 검색 처리 부분 시작 */
        BooleanBuilder andBuilder = new BooleanBuilder();

        String cateCd = search.getCateCd();
        List<String> cateCds = search.getCateCds();
        List<BookStatus> statuses = search.getStatuses();
        BookStatus status = search.getStatus();
        Long bookNo = search.getBookNo();
        String searchOpt = search.getSearchOpt();
        String searchKey = search.getSearchKey();

        /* 도서 분류 검색 부분 S */
        if (cateCd != null && !cateCd.isBlank()) {
            andBuilder.and(books.category.cateCd.eq(cateCd));
        }
        if (cateCds != null && cateCds.size() > 0) {
            andBuilder.and(books.category.cateCd.in(cateCds));
        }
        /* 도서 분류 검색 부분 E */

        /* 판매 상태 검색 부분 S */
        if (status != null) {
            andBuilder.and(books.status.eq(status));
        }

        if (statuses != null && statuses.size() > 0) {
            andBuilder.and(books.status.in(statuses));
        }
        /* 판매 상태 검색 부분 E */

        /* 도서 번호 */
        if (bookNo != null) {
            andBuilder.and(books.bookNo.eq(bookNo));
        }

        /* 조건 및 키워드 검색 S */
        if (searchOpt != null && !searchOpt.isBlank() && searchKey != null && !searchKey.isBlank()) {
            searchOpt = searchOpt.trim();
            searchKey = searchKey.trim();

            if (searchOpt.equals("all")) { // 통합 검색_관리자용 (문자열 포함 여부 확인)
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(books.bookNo.stringValue().contains(searchKey))
                        .or(books.bookNm.containsIgnoreCase(searchKey))
                        .or(books.author.containsIgnoreCase(searchKey))
                        .or(books.publisher.containsIgnoreCase(searchKey));
                andBuilder.and(orBuilder);
            } else if (searchOpt.equals("frontAll")) { // 통합 검색_홈페이지용 (문자열 포함 여부 확인)
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(books.bookNm.containsIgnoreCase(searchKey))
                        .or(books.author.containsIgnoreCase(searchKey))
                        .or(books.publisher.containsIgnoreCase(searchKey));
                andBuilder.and(orBuilder);
            } else if (searchOpt.equals("bookNm")) { // 도서명으로 검색
                andBuilder.and(books.bookNm.containsIgnoreCase(searchKey));
            } else if (searchOpt.equals("bookNm")) { // 도서번호로 검색
                andBuilder.and(books.bookNm.containsIgnoreCase(searchKey));
            } else if (searchOpt.equals("bookNm")) { // 저자명으로 검색
                andBuilder.and(books.bookNm.containsIgnoreCase(searchKey));
            } else if (searchOpt.equals("bookNm")) { // 출판사로 검색
                andBuilder.and(books.bookNm.containsIgnoreCase(searchKey));
            }
        } else if (searchOpt != null && !searchOpt.isBlank()) { // 카테고리 검색
            if (searchOpt.equals("domestic")) { // 국산도서 전체 검색
                // 국산도서 분류코드 D
                andBuilder.and(books.category.cateCd.containsIgnoreCase("D"));
            } else if (searchOpt.equals("D001")) { // 소설
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D002")) { // 시/에세이
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D003")) { // 인문
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D004")) { // 경영/경제
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D005")) { // 자기계발
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D006")) { // 정치/사회
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D007")) { // 역사/문화
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            } else if (searchOpt.equals("D008")) { // 외국어
                andBuilder.and(books.category.cateCd.containsIgnoreCase(searchOpt));
            }
            if (searchOpt.equals("foregin")) { // 해외도서 전체 검색
                // 해외도서 분류코드 F
                andBuilder.and(books.category.cateCd.containsIgnoreCase("F"));
            }
        }
        /* 조건 및 키워드 검색 E */
        /* 검색 처리 부분 끝 */

        /* 정렬 처리 S */
        // listOrder_DESC,createdAt_ASC
        // List로 선언하여 여러 개의 정렬 조건 추가 가능
        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        String sort = search.getSort();
        if (sort != null && !sort.isBlank()) {
            List<String[]> sorts = Arrays.stream(sort.trim().split(","))
                    .map(s -> s.split("_")).toList();
            PathBuilder pathBuilder = new PathBuilder(Books.class, "book");

            for (String[] _sort : sorts) {
                Order direction = Order.valueOf(_sort[1].toUpperCase()); // 정렬 방향
                orderSpecifier.add(new OrderSpecifier(direction, pathBuilder.get(_sort[0])));
            }
        }
        /* 정렬 처리 E */
        JPAQueryFactory factory = new JPAQueryFactory(em);
        List<Books> items = factory.selectFrom(books)
                .leftJoin(books.category)
                .fetchJoin()
                .limit(limit)
                .offset(offset)
                .where(andBuilder)
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .fetch();

        ListData<Books> data = new ListData<>();
        data.setContent(items);

        /* Todo : 페이징 처리 로직 추가 */
        int total = (int) booksRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, total, 10, limit, request); // 한페이지에 10개
        data.setPagination(pagination);

        return data;
    }


    /**
     * 첨부 이미지 추가처리
     * @param books
     */
    public void addFileInfo(Books books){
        String gid = books.getGid();
        List<FileInfo> mainImages = fileInfoService.getListDone(gid, "main");
        List<FileInfo> listImages = fileInfoService.getListDone(gid, "list");
        List<FileInfo> editorImages = fileInfoService.getListDone(gid, "editor");
        books.setMainImages(mainImages);
        books.setListImages(listImages);
        books.setEditorImages(editorImages);
    }
}
