package ua.vholovetskyi.bookshop.order.mapper;

import ua.vholovetskyi.bookshop.order.controller.dto.OrderDto;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSummary;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;
import ua.vholovetskyi.bookshop.order.validator.OrderJson;

import java.time.LocalDate;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
public class OrderFactory {

    public static OrderEntity createNewOrder(OrderDto orderDto) {
        return OrderEntity.builder()
                .customerId(orderDto.getCustomerId())
                .grossValue(orderDto.getGrossValue())
                .status(orderDto.getStatus())
                .orderDate(orderDto.getOrderDate())
                .build();
    }

    public static OrderEntity createNewOrder(Long id, OrderDto orderDto) {
        return OrderEntity.builder()
                .id(id)
                .customerId(orderDto.getCustomerId())
                .grossValue(orderDto.getGrossValue())
                .status(orderDto.getStatus())
                .build();
    }

    public static OrderEntity createNewOrder(OrderJson orderJson) {
        return OrderEntity.builder()
                .customerId(orderJson.getCustId())
                .grossValue(orderJson.getGrossValue())
                .status(OrderStatus.parseString(orderJson.getStatus()).orElseThrow())
                .orderDate(LocalDate.parse(orderJson.getOrderDate()))
                .build();
    }

    public static OrderSummary createOrderSummary(OrderEntity newOrder) {
        return OrderSummary.builder()
                .id(newOrder.getId())
                .orderDate(newOrder.getOrderDate())
                .status(newOrder.getStatus())
                .grossValue(newOrder.getGrossValue())
                .build();
    }
}
