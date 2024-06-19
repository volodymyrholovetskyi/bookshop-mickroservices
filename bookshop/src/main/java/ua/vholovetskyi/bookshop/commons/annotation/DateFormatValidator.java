package ua.vholovetskyi.bookshop.commons.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
public class DateFormatValidator implements ConstraintValidator<DateFormatValidation, String> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String orderDate, ConstraintValidatorContext constraintContext) {
        if (orderDate == null) {
            return false;
        }
        try {
            LocalDate.parse(orderDate, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            constraintContext.buildConstraintViolationWithTemplate("Date format is not valid!").addConstraintViolation();
            return false;
        }
        return true;
    }
}
