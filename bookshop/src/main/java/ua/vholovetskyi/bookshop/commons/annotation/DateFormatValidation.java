package ua.vholovetskyi.bookshop.commons.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
@Target(FIELD)
public @interface DateFormatValidation {
    public String message() default "Invalid date format. Sample date format: yyyy-MM-dd";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
