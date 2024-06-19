package ua.vholovetskyi.bookshop.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.data.OrderBuilder;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;
import ua.vholovetskyi.bookshop.order.service.handler.OrderSearchService;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {ExportOrderService.class, OrderSearchService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ExportOrderServiceIT extends OrderBuilder {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ExportOrderService orderService;

    @Test
    void shouldExportAllOrders_byCustomerId() {
        //given
        givenSaveOrders();
        var orderDto = givenSearchRequest();

        //when
        var orders = orderService.exportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(4);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndStatus() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderRequestByCustIdAndStatus();

        //when
        var orders = orderService.exportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(4);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndOrderDate() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderRequestByCustIdAndOrderDate();

        //when
        var orders = orderService.exportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    void shouldFindAllOrders_byCustomerIdAndStatusAndOrderDate() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderRequestByCustIdAndStatusAndOrderDate();

        //when
        var orders = orderService.exportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnEmptyList_ifCustomerNotFound() {
        //given
        givenSaveOrders();
        var orderDto = givenOrderRequestWithCustId3();

        //when
        var orders = orderService.exportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnEmptyList_ifCustomerDBEmpty() {
        //given
        var orderDto = givenOrderSearchCustId();

        //when
        var orders = orderService.exportOrders(orderDto);

        //then
        assertThat(orders.size()).isEqualTo(0);
    }

    private void givenSaveOrders() {
        customerRepository.save(givenCustomer());
        orderRepository.saveAll(givenOrders());
    }
}
