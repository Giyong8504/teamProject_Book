package org.teamproject.controllers.admin.dtos;

import lombok.Data;

@Data
public class CartListForm {
    private Long cartItemId;
    private String bookNm;
    private int price;
    private int count;
    private String imgUrl;
}
