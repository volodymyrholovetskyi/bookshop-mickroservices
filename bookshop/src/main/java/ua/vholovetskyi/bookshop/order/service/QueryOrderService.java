package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.vholovetskyi.bookshop.commons.exception.impl.order.OrderNotFoundException;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSearchRequest;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSearchResponse;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;
import ua.vholovetskyi.bookshop.order.service.handler.OrderSearchService;

import static ua.vholovetskyi.bookshop.order.mapper.OrderDtoMapper.mapToOrderSearch;


/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
@Service
@RequiredArgsConstructor
public class QueryOrderService {

    private final OrderRepository orderRepository;
    private final OrderSearchService searchService;

    public OrderEntity findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderSearchResponse findOrders(OrderSearchRequest searchRequest) {
        var pageOrder = searchService.searchOrders(searchRequest.getSearch(), getPageRequest(searchRequest));
        return mapToOrderSearch(pageOrder.getContent(), pageOrder.getTotalElements());
    }

    private PageRequest getPageRequest(OrderSearchRequest orderRequest) {
        return PageRequest.of(orderRequest.getPageNumber(), orderRequest.getSize());
    }
}
