package org.teamproject.controllers.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductForm {

    private String mode; // 수정시 사용
    private String category;

    @NotBlank
    private String productNm;

    @NotBlank
    private String gid = UUID.randomUUID().toString(); // 없으면 생성.

    private int price;
    private int stock; // 재고

    private String status;

    private String description;

    private long listOrder;

    private boolean dc; // 할인 사용 여부
    private boolean sp; // 판매 기간 사용 여부
    private boolean ps; // 새제품, 중고품 여부



}
