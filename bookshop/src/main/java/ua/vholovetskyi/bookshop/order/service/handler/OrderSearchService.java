package ua.vholovetskyi.bookshop.order.service.handler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.bookshop.order.controller.dto.SearchRequest;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderSearchService {

    private final List<OrderSearchHandler> handlers;

    public OrderSearchService(OrderRepository orderRepository) {
        handlers = List.of(
                new CustIDSearchHandler(orderRepository),
                new OrderDateSearchHandler(orderRepository),
                new StatusSearchHandler(orderRepository),
                new FullSearchHandler(orderRepository)
        );
    }

    public Page<OrderEntity> searchOrders(SearchRequest orderFiltering, Pageable pageable) {
        var currentHandler = getCurrentHandler(orderFiltering);
        return currentHandler.handle(orderFiltering, pageable);
    }

    private OrderSearchHandler getCurrentHandler(SearchRequest filteringDto) {
        Optional<OrderSearchHandler> currentHandler = Optional.empty();
        for (OrderSearchHandler handler : handlers) {
            if (handler.supports(filteringDto)) {
                currentHandler = Optional.of(handler);
                break;
            }
        }
        return currentHandler
                .orElseThrow(() -> new IllegalArgumentException("Unsupported fields!"));
    }
}
