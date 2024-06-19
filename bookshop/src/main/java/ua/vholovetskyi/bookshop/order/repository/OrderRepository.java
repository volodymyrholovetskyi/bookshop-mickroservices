package ua.vholovetskyi.bookshop.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByCustomerId(Long customerId,
                                          Pageable pageable);

    Page<OrderEntity> findAllByCustomerIdAndStatus(
            Long customerId,
            OrderStatus status,
            Pageable pageable);

    Page<OrderEntity> findAllByCustomerIdAndOrderDateIsBetween(
            Long customerId,
            LocalDate from,
            LocalDate to,
            Pageable pageable);

    Page<OrderEntity> findAllByCustomerIdAndOrderDateIsBetweenAndStatus(
            Long customerId,
            LocalDate from,
            LocalDate to,
            OrderStatus status,
            Pageable pageable);
}
