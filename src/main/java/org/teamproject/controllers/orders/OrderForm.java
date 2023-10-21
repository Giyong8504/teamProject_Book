package org.teamproject.controllers.orders;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.teamproject.entities.OrderItem;

import java.util.List;

@Data
public class OrderForm {
    private Long id; // 주문 번호

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

    @NotBlank
    private String paymentType = "LBT";

    private int totalPrice;
    private int payPrice;

    private String deliveryCompany;
    private String invoice;

    private List<OrderItem> orderItems;

    private List<Long> itemId;
}
