package ua.vholovetskyi.bookshop.order.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-24
 */
@Component
@RequiredArgsConstructor
public class OrderJsonValidator {
    private final Validator validator;

    public boolean validateOrder(OrderJson orderJson) {
        Set<ConstraintViolation<OrderJson>> validate = validator.validate(orderJson);
        return validate.isEmpty();
    }
}
