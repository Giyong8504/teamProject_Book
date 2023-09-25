package org.teamproject.controllers.admin.dtos;

import lombok.Data;

@Data
public class CategoryForm {
    private String mode;
    private String cateCd;
    private String cateNm;
    private boolean open;
}
