package org.teamproject.commons.constants;

public enum PaymentType {
    LBT("무통장입금"), // 무통장 임금
    DIRECT("계좌이체"), // 계좌이체
    VACCOUNT("가상계좌"), // 가상 계좌
    CARD("신용카드"); // 신용카드

    private String title;

    PaymentType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
