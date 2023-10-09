package org.teamproject.commons.constants;

import org.teamproject.commons.Utils;

import java.util.Arrays;
import java.util.List;

public enum BookStatus {
    ON_SALE, // 판매중
    STOP, // 판매중지

    READY, // 상품 준비중

    OUT_OF_STOCK; // 품절


    public String getString() {
        return Utils.getMessage("BookStatus." + name(), "common");
    }

    public static List<String[]> getList() {
        return Arrays.asList(
                new String[]{ON_SALE.name(),ON_SALE.getString()},
                new String[]{STOP.name(),STOP.getString()},
                new String[]{READY.name(),READY.getString()},
                new String[]{OUT_OF_STOCK.name(),OUT_OF_STOCK.getString()}
        );
    }
}
