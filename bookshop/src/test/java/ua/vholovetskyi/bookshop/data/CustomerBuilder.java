package ua.vholovetskyi.bookshop.data;

import org.junit.jupiter.params.provider.Arguments;
import ua.vholovetskyi.bookshop.customer.controller.dto.CustomerDto;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;

import java.util.List;
import java.util.stream.Stream;

public abstract class CustomerBuilder {

    protected static final String CUSTOMER_NOT_FOUND_MESSES = "Customer with ID: 1 not found!";
    protected static final String EMAIL_ALREADY_EXISTS_MESSES = "Customer with email: [first@gmail.com] already exists!";
    protected CustomerDto givenCustomerDto() {
        return CustomerDto.builder()
                .firstName("First")
                .lastName("Customer")
                .email("first@gmail.com")
                .phone("0987656432")
                .street("Street")
                .city("City")
                .zipCode("23-567")
                .build();
    }

    protected CustomerEntity givenCustomer() {
        return new CustomerEntity(
                1L,
                "First",
                "Customer",
                "first@gmail.com",
                "0987656432",
                "Street",
                "City",
                "23-567"
        );
    }

    protected List<CustomerEntity> givenCustomers() {
        return List.of(
                new CustomerEntity(
                        1L,
                        "First",
                        "Customer",
                        "first@gmail.com",
                        "0987656432",
                        "Street",
                        "City",
                        "23-567"
                ),
                new CustomerEntity(
                        2L,
                        "Second",
                        "Customer",
                        "second@gmail.com",
                        "0987656432",
                        "Street",
                        "City",
                        "23-567"
                ));
    }

    protected static Stream<Arguments> wrongCustomers() {
        return Stream.of(
                Arguments.of(CustomerDto.builder()
                        .firstName("First")
                        .lastName("Customer")
                        .email("firstgamil.com")
                        .phone("0987656432")
                        .street("Street")
                        .city("City")
                        .zipCode("23-567")
                        .build()),
                Arguments.of(CustomerDto.builder()
                        .firstName("First")
                        .lastName("Customer")
                        .email("first@gamil.com")
                        .phone("0987ADDG432")
                        .street("Street")
                        .city("City")
                        .zipCode("23-567")
                        .build()),
                Arguments.of(CustomerDto.builder()
                        .firstName(" ")
                        .lastName("Customer")
                        .email("first@gamil.com")
                        .phone("0987656432")
                        .street("Street")
                        .city("City")
                        .zipCode("23-567")
                        .build()),
                Arguments.of(CustomerDto.builder()
                        .firstName("First")
                        .lastName(" ")
                        .email("first@gamil.com")
                        .phone("0987656432")
                        .street("Street")
                        .city("City")
                        .zipCode("23-567")
                        .build()),
                Arguments.of(CustomerDto.builder().build()));
    }
}
