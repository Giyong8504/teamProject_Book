package org.teamproject.controllers.orders;

import lombok.Data;
import org.teamproject.commons.constants.OrdersStatus;

@Data
public class OrdersSearch {
    private int page = 1;
    private int limit = 20;
    private String sort = "createdAt_DESC"; // 정렬

    // 단일 주문 번호 조회
    private Long id;

    // 여러 주문번호 조회 - in
    private Long[] ids;
    private String searchOpt = "all"; // 검색 옵션
    private String searchKey; // 검색 키워드
    private OrdersStatus[] statuses;
    private OrdersStatus status;
}