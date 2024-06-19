package ua.vholovetskyi.bookshop.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResponse {
    private List<OrderList> list;
    private long totalOrders;
}
