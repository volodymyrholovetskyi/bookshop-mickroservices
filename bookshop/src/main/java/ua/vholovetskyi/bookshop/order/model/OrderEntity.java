package ua.vholovetskyi.bookshop.order.model;

import jakarta.persistence.*;
import lombok.*;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderDto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-25
 */
@Getter
@Setter
@Table(name = "orders")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -1446398935944895849L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq_gen")
    @SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_order_id_seq", allocationSize = 1)
    @Column(name = "order_id", nullable = false)
    private Long id;
    @Column(name = "cust_id", nullable = false)
    private Long customerId;
    @Enumerated(EnumType.STRING)
    @Column(length = 70, nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private BigDecimal grossValue;
    @Column(nullable = false)
    private LocalDate orderDate;

    public void updateFields(OrderDto orderDto) {
        if (customerId != null) {
            this.customerId = orderDto.getCustomerId();
        }
        if (orderDto.getStatus() != null) {
            this.status = orderDto.getStatus();
        }
        if (orderDto.getGrossValue() != null) {
            this.grossValue = orderDto.getGrossValue();
        }
        if (orderDto.getOrderDate() != null){
            this.orderDate = orderDto.getOrderDate();
        }
    }
}

