package org.teamproject.models.product;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.product.Order;
import org.teamproject.models.files.FileNotFoundException;
import org.teamproject.repositories.OrderRepository;


@Service
@RequiredArgsConstructor
public class OrderInfoService {

    private final OrderRepository repository;

    public Order get(Long id) {
        Order item = repository.findById(id).orElseThrow(FileNotFoundException::new);

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
