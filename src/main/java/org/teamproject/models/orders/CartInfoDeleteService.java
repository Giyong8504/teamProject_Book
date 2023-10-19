package org.teamproject.models.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.CartInfo;
import org.teamproject.repositories.CartInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartInfoDeleteService {
    private final CartInfoRepository repository;
    private final CartInfoService infoService;

    /**
     * 장바구니 비우기
     *
     * @param mode : direct - 바로구매 상품, cart - 장바구니 상품
     */

    public void deleteAll(String mode) {
        List<CartInfo> items = infoService.getList(mode);
        repository.deleteAll(items);
        repository.flush();
    }
}
