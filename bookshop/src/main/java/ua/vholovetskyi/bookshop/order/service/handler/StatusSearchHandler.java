package ua.vholovetskyi.bookshop.order.service.handler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.vholovetskyi.bookshop.order.controller.dto.SearchRequest;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import java.util.Objects;

public class StatusSearchHandler implements OrderSearchHandler {

    private final OrderRepository orderRepository;

    public StatusSearchHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean supports(SearchRequest filter) {
        return Objects.nonNull(filter.getStatus())
                && Objects.isNull(filter.getFrom())
                && Objects.isNull(filter.getTo());
    }

    @Override
    public Page<OrderEntity> handle(SearchRequest filter, Pageable pageable) {
        return orderRepository.findAllByCustomerIdAndStatus(
                filter.getCustomerId(),
                filter.getStatus(),
                pageable);
    }
}
