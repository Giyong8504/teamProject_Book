package org.teamproject.controllers.admin.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CategoryForm {
    private String mode;
    private String cateCd;
    private String cateNm;
    private boolean open;

    List<Integer> chkNo;
    List<String> cateCds;
}
