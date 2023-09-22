package org.teamproject.controllers.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookForm {
    @NotBlank
    private String bookId;

    @NotBlank
    private String bookNm;

    @NotBlank
    private Long price;
}
