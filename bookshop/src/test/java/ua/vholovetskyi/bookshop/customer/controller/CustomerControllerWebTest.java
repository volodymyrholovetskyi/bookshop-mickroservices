package ua.vholovetskyi.bookshop.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.vholovetskyi.bookshop.customer.controller.dto.CustomerDto;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.customer.service.CustomerService;
import ua.vholovetskyi.bookshop.data.CustomerBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CustomerController.class})
class CustomerControllerWebTest extends CustomerBuilder {

    @MockBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mock;

    @Test
    void shouldFindAllCustomers() throws Exception {
        //given
        var customers = givenCustomers();
        when(customerService.findAll()).thenReturn(customers);

        //expect
        mock.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldCrateCustomers() throws Exception {
        //given
        var customer = givenCustomer();
        var customerDto = givenCustomerDto();
        when(customerService.createCustomer(any(CustomerEntity.class))).thenReturn(customer);

        //expect
        mock.perform(post("/api/customers")
                        .content(jsonString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("first@gmail.com"));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        //given
        var customer = givenCustomer();
        var customerDto = givenCustomerDto();
        when(customerService.updateCustomer(any(CustomerEntity.class))).thenReturn(customer);

        //expect
        mock.perform(put("/api/customers/1")
                        .content(jsonString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.email").value("first@gmail.com"));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        //given
        var customer = givenCustomer();
        when(customerService.updateCustomer(any(CustomerEntity.class))).thenReturn(customer);

        //expect
        mock.perform(delete("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private String jsonString(CustomerDto customerDto) throws JsonProcessingException {
        return mapper.writeValueAsString(customerDto);
    }
}
