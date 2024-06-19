package ua.vholovetskyi.bookshop.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.bookshop.commons.exception.impl.customer.CustomerNotFoundException;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.data.OrderBuilder;
import ua.vholovetskyi.bookshop.order.controller.dto.OrderSummary;
import ua.vholovetskyi.bookshop.commons.exception.impl.order.OrderNotFoundException;
import ua.vholovetskyi.bookshop.order.model.OrderStatus;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {OrderService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderServiceIT extends OrderBuilder {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {
        //given
        givenSaveCustomer();
        var orderDto = givenOrderDto();

        //when
        OrderSummary order = orderService.createOrder(orderDto);

        //then
        assertThat(order.getId()).isEqualTo(1L);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(order.getTotalProduct()).isEqualTo(2);
    }

    @Test
    void shouldThrowsException_whenCreateOrder_ifCustomerNotFound() {
        //given
        var orderDto = givenOrderDto();

        //when
        var customerNotFound = assertThrows(CustomerNotFoundException.class,
                () -> orderService.createOrder(orderDto));

        //then
        assertThat(customerNotFound.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND_MESSES);
    }

    @Test
    void shouldUpdateOrder() {
        //given
        giveSaveOrderWithCustomer();
        var orderDto = givenUpdateOrder();

        //when
        orderService.updateOrder(1L, orderDto);
        var updateOrder = orderRepository.findById(1L);

        //then
        assertThat(updateOrder.get().getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(updateOrder.get().getItems().size()).isEqualTo(1);
    }

    @Test
    void shouldThrowsException_whenOrderNotFound() {
        //given
        givenSaveCustomer();
        var orderDto = givenUpdateOrder();

        //when
        var orderNotFound = assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrder(1L, orderDto));

        //then
        assertThat(orderNotFound.getMessage()).isEqualTo(ORDER_NOT_FOUND_MESSES);
    }

    @Test
    void shouldThrowsException_whenUpdateOrder_ifOrderNotFound() {
        //given
        givenSaveCustomer();
        var orderDto = givenUpdateOrder();

        //when
        var orderNotFound = assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrder(1L, orderDto));

        //then
        assertThat(orderNotFound.getMessage()).isEqualTo(ORDER_NOT_FOUND_MESSES);
    }

    @Test
    void shouldDeleteOrder() {
        //given
        giveSaveOrderWithCustomer();

        //when
        orderService.deleteOrder(1L);
        var deletedOrder = orderRepository.findById(1L);

        //then
        assertThat(deletedOrder.isEmpty()).isTrue();
    }

    private void givenSaveCustomer() {
        customerRepository.save(givenCustomer());
    }

    private void giveSaveOrderWithCustomer() {
        givenSaveCustomer();
        orderRepository.save(givenOrder());
    }
}