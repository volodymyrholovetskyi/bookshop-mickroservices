package ua.vholovetskyi.bookshop.commons.exception.impl.customer;

import ua.vholovetskyi.bookshop.commons.exception.ResourceNotFound;

public class CustomerNotFoundException extends ResourceNotFound {
    public CustomerNotFoundException(Long id) {
        super("Customer with ID: %d not found!".formatted(id));
    }
}
