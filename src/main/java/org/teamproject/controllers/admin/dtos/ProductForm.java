package org.teamproject.controllers.admin.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.teamproject.commons.constants.BookStatus;
import org.teamproject.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class ProductForm {

    private String mode; // 폼 작업(추가,수정시 사용)
    private String cateCd; // 도서 분류 코드
    private Long bookNo; // 도서 번호
    private String isbn;

    @NotBlank
    private String gid = UUID.randomUUID().toString(); // 없으면 생성.

    @NotBlank
    private String bookNm;

    private String author; // 저자
    private String publisher; // 출판사
    private String publicationDt;
    private String origin;

    private int price;
    private int stock; // 재고
    private String status = BookStatus.READY.name(); // 기본값은 상품 준비중(READY);
    private String description; // 상세설명

    private long listOrder;

    private List<FileInfo> mainImages; // 상품 메인 이미지
    private List<FileInfo> listImages; // 목록 이미지
    private List<FileInfo> editorImages; // 에디터 이미지
}
