package org.teamproject.controllers.member.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OrderForm {
    private Long orderNo;

    private List<Long> cartNo;

    @NotBlank
    private String orderName;

    /* 주문자 */
    @NotBlank
    private String orderEmail;
    @NotBlank
    private String orderMobile;

    /* 받는분 */
    @NotBlank
    private String receiverName;
    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String zonecode;
    @NotBlank
    private String address;
    @NotBlank
    private String addressSub;

}
