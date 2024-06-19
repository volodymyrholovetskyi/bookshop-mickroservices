package ua.vholovetskyi.bookshop.customer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.bookshop.commons.exception.impl.customer.CustomerNotFoundException;
import ua.vholovetskyi.bookshop.commons.exception.impl.customer.EmailAlreadyExistsException;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;
import ua.vholovetskyi.bookshop.data.CustomerBuilder;
import ua.vholovetskyi.bookshop.order.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {CustomerService.class})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerServiceIT extends CustomerBuilder {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void should_find_all_customers() {
        //given
        givenSaveCustomers();

        //when
        var findCustomers = customerService.findAll();

        //then
        assertThat(findCustomers.size()).isEqualTo(2);
    }

    @Test
    void should_create_customer() {
        //given
        var customer = givenCustomer();

        //when
        customerService.createCustomer(customer);

        //then
        assertThat(customerService.findAll().size()).isEqualTo(1);
    }

    @Test
    void should_throws_exception_when_create_customer_if_email_exists() {
        //given
        var customer = givenSaveCustomer();

        //when
        var emailException = assertThrows(EmailAlreadyExistsException.class,
                () -> customerService.createCustomer(customer));

        //then
        assertThat(emailException.getMessage()).isEqualTo(EMAIL_ALREADY_EXISTS_MESSES);
    }

    @Test
    void should_update_customer_email() {
        //given
        var customer = givenSaveCustomer();
        customer.setEmail("first1@gmail.com");

        //when
        var updatedCustomer = customerService.updateCustomer(customer);

        //then
        assertThat(updatedCustomer.getEmail()).isEqualTo("first1@gmail.com");
    }

    @Test
    void should_throws_exception_when_update_customer_if_email_exists() {
        //given
        givenSaveCustomers();
        var customer = givenCustomer();
        customer.setId(2L);

        //when
        var emailException = assertThrows(EmailAlreadyExistsException.class,
                () -> customerService.updateCustomer(customer));

        //then
        assertThat(emailException.getMessage()).isEqualTo(EMAIL_ALREADY_EXISTS_MESSES);
    }

    @Test
    void should_update_customer_if_email_is_the_same() {
        //given
        var customer = givenSaveCustomer();

        //when
        var updateCustomer = customerService.updateCustomer(customer);

        //then
        assertThat(updateCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void should_throws_exception_when_update_customer_if_customer_dos_not_exists() {
        //given
        var customer = givenCustomer();

        //when
        var customerNotFound = assertThrows(CustomerNotFoundException.class,
                () -> customerService.updateCustomer(customer));

        //then
        assertThat(customerNotFound.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND_MESSES);
    }

    @Test
    void should_delete_customer() {
        //given
        givenSaveCustomer();

        //when
        customerService.deleteById(1L);
        var deletedCustomer = orderRepository.findById(1L);

        //then
        assertThat(deletedCustomer.isEmpty()).isTrue();
    }

    private CustomerEntity givenSaveCustomer() {
        return customerRepository.save(givenCustomer());
    }


    private void givenSaveCustomers() {
        customerRepository.saveAll(givenCustomers());
    }
}