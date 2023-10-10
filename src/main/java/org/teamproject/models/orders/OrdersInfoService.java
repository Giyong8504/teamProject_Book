package org.teamproject.models.orders;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.product.Orders;
import org.teamproject.models.files.FileNotFoundException;
import org.teamproject.repositories.OrdersRepository;


@Service
@RequiredArgsConstructor
public class OrdersInfoService {

    private final OrdersRepository repository;

    public Orders get(Long id) {
        Orders item = repository.findById(id).orElseThrow(FileNotFoundException::new);

        return item;
    }


    @Data
    @Builder
    static class Options { // 여기서만 사용하므로 내부클래스 작성.
        private String orderNo;
        private String userInfo;
        private SearchMode mode = SearchMode.ALL;
    }

    static enum SearchMode {
        ALL,
        DONE,
        UNDONE
    }
}
