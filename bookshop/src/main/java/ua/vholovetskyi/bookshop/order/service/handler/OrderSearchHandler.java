package ua.vholovetskyi.bookshop.order.service.handler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.vholovetskyi.bookshop.order.controller.dto.SearchRequest;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;

public interface OrderSearchHandler {

    public boolean supports(SearchRequest filteringDto);

    public Page<OrderEntity> handle(SearchRequest filter, Pageable pageable);
}
