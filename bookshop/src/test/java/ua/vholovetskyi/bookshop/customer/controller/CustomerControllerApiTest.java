package ua.vholovetskyi.bookshop.customer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ua.vholovetskyi.bookshop.commons.exception.ErrorResponse;
import ua.vholovetskyi.bookshop.customer.controller.dto.CustomerDto;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.customer.service.CustomerService;
import ua.vholovetskyi.bookshop.data.CustomerBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CustomerControllerApiTest extends CustomerBuilder {

    @LocalServerPort
    private int port;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldFindAllCustomers() {
        //given
        var baseUrl = givenBaseUrl();
        var customers = givenCustomers();
        var type = new ParameterizedTypeReference<List<CustomerEntity>>() {
        };

        //when
        when(customerService.findAll()).thenReturn(customers);
        RequestEntity<?> request = RequestEntity.get(URI.create(baseUrl)).build();
        ResponseEntity<List<CustomerEntity>> result = restTemplate.exchange(request, type);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).size().isEqualTo(2);
        verify(customerService, times(1)).findAll();
    }

    @Test
    void shouldCreateCustomer() {
        //given
        var baseUrl = givenBaseUrl();
        var customerDto = givenCustomerDto();
        var customer = givenCustomer();

        //when
        when(customerService.createCustomer(any(CustomerEntity.class))).thenReturn(customer);
        RequestEntity<CustomerDto> request = RequestEntity.post(URI.create(baseUrl)).body(customerDto);
        ResponseEntity<CustomerEntity> result = restTemplate.exchange(request, CustomerEntity.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(201);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(customerService, times(1)).createCustomer(any(CustomerEntity.class));
    }

    @ParameterizedTest
    @MethodSource("wrongCustomers")
    void shouldNotCreateCustomer(CustomerDto customer) {
        //given
        var baseUrl = givenBaseUrl();

        //when
        RequestEntity<CustomerDto> request = RequestEntity.post(URI.create(baseUrl)).body(customer);
        ResponseEntity<ErrorResponse> result = restTemplate.exchange(request, ErrorResponse.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldUpdateCustomer() {
        //given
        var url = givenBaseUrl() + "/1";
        var customer = givenCustomer();
        var customerDto = givenCustomerDto();

        //when
        when(customerService.updateCustomer(any(CustomerEntity.class))).thenReturn(customer);
        RequestEntity<CustomerDto> request = RequestEntity.put(URI.create(url)).body(customerDto);
        ResponseEntity<CustomerEntity> result = restTemplate.exchange(request, CustomerEntity.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(202);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(customerService, times(1)).updateCustomer(any(CustomerEntity.class));
    }

    @ParameterizedTest
    @MethodSource("wrongCustomers")
    void shouldNotUpdateCustomer(CustomerDto customer) {
        //given
        var url = givenBaseUrl() + "/1";

        //when
        RequestEntity<CustomerDto> request = RequestEntity.put(URI.create(url)).body(customer);
        ResponseEntity<ErrorResponse> result = restTemplate.exchange(request, ErrorResponse.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(400);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldDeleteCustomer() {
        //given
        var url = givenBaseUrl() + "/1";

        //when
        RequestEntity<?> request = RequestEntity.delete(URI.create(url)).build();
        ResponseEntity<?> result = restTemplate.exchange(request, Void.class);

        //then
        assertThat(result.getStatusCode().value()).isEqualTo(204);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(customerService, times(1)).deleteById(any(Long.class));
    }

    private String givenBaseUrl() {
        return "http://localhost:" + port + "/api/customers";
    }
}
