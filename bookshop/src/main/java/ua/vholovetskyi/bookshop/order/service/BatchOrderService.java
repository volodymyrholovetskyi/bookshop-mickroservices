package ua.vholovetskyi.bookshop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.order.model.OrderEntity;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchOrderService {

    private final OrderRepository orderRepository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Transactional
    public void batchProcessing(List<OrderEntity> orders, boolean isFinished) {
        if (!orders.isEmpty() && (orders.size() % batchSize == 0 || isFinished))
            saveAll(orders);
    }

    private void saveAll(List<OrderEntity> orders) {
        var savedOrders = orderRepository.saveAllAndFlush(orders);
        orders.clear();
        log.info("Batch of %s orders...".formatted(savedOrders.size()));
    }
}
