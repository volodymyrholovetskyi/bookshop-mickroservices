package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

import java.time.LocalDate;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @NotNull(message = "{order.field.required}")
    private Long customerId;
    private OrderStatus status;
    private LocalDate from;
    private LocalDate to;
}
