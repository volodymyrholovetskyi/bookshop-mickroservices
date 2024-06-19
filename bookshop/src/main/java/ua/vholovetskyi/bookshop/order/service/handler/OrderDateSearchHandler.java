package ua.vholovetskyi.bookshop.order.service.handler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.vholovetskyi.bookshop.order.controller.dto.SearchRequest;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import java.util.Objects;

public class OrderDateSearchHandler implements OrderSearchHandler {

    private final OrderRepository orderRepository;

    public OrderDateSearchHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean supports(SearchRequest filter) {
        return Objects.isNull(filter.getStatus())
                && Objects.nonNull(filter.getFrom())
                && Objects.nonNull(filter.getTo());
    }

    @Override
    public Page<OrderEntity> handle(SearchRequest filter, Pageable pageable) {
        return orderRepository.findAllByCustomerIdAndOrderDateIsBetween(filter.getCustomerId(),
                filter.getFrom(),
                filter.getTo(),
                pageable);
    }
}
