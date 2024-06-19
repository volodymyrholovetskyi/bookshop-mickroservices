package ua.vholovetskyi.bookshop.order.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchRequest {

    @NotNull(message = "{order.field.required}")
    private SearchRequest search;
    @NotNull(message = "{order.field.required}")
    private Integer pageNumber;
    @NotNull(message = "{order.field.required}")
    @Min(value = 1L, message = "{order.min.size}")
    private Integer size;
}
