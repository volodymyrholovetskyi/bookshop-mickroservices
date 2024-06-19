package ua.vholovetskyi.bookshop.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {

    private Long id;
    private OrderStatus status;
    private BigDecimal grossValue;
    private LocalDate orderDate;
}
