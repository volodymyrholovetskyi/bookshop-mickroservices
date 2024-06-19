package ua.vholovetskyi.bookshop.order.service.handler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.vholovetskyi.bookshop.order.controller.dto.SearchRequest;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import java.util.Objects;

public class FullSearchHandler implements OrderSearchHandler {

    private final OrderRepository orderRepository;

    public FullSearchHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean supports(SearchRequest filter) {
        return Objects.nonNull(filter.getStatus())
                && Objects.nonNull(filter.getFrom())
                && Objects.nonNull(filter.getTo());
    }

    @Override
    public Page<OrderEntity> handle(SearchRequest filter, Pageable pageable) {
        return orderRepository.findAllByCustomerIdAndOrderDateIsBetweenAndStatus(
                filter.getCustomerId(),
                filter.getFrom(),
                filter.getTo(),
                filter.getStatus(),
                pageable);
    }
}
