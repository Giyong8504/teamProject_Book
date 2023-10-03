package org.teamproject.controllers.admin.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemForm {
    @NotNull(message="상품 아이디는 필수 입력 값입니다.")
    private Long bookId;

    @Min(value = 1, message="최소 1개 이상 담아주세요.")
    private int count;
}
