package org.teamproject.controllers.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.teamproject.entities.Member;

@Data
public class BookForm {
    @NotBlank
    private String bookId;

    @NotBlank
    private String bookNm;

    private String bookDesc; // 상품 설명 , 현재는 없어도 상관 없다 생각해서 NotBlank하지 않았음

    @NotBlank
    private Long price;

    @NotBlank
    private boolean bookStatus;

    @NotBlank
    private Member email; // 판매자 아이디 (판매하는 사람 정보)
}
