package ua.vholovetskyi.bookshop.commons.exception.impl.customer;

import ua.vholovetskyi.bookshop.commons.exception.ResourceAlreadyExists;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
public class EmailAlreadyExistsException extends ResourceAlreadyExists {
    public EmailAlreadyExistsException(String email) {
        super("Customer with email: [%s] already exists!".formatted(email));
    }
}
