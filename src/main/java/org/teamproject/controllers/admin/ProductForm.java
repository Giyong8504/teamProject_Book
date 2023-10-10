package org.teamproject.controllers.admin;

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

    @NotBlank
    private String productNm; // 상품명

    @NotBlank
    private String author; // 저자

    @NotBlank
    private String publisher; // 출판사

    @NotBlank
    private String gid = UUID.randomUUID().toString(); // 없으면 생성.

    private int price;
    private int stock; // 재고

    private String status = BookStatus.READY.name(); // 기본값은 상품 준비중(READY);

    private String description; // 상세설명

    private long listOrder;

    private List<FileInfo> mainImages; // 상품 메인 이미지

    private List<FileInfo> listImages; // 목록 이미지

    private List<FileInfo> editorImages; // 에디터 이미지

    private boolean dc; // 할인 사용 여부
    private boolean sp; // 판매 기간 사용 여부
    private boolean ps; // 새제품, 중고품 여부

    private List<Integer> chkNo;



}
