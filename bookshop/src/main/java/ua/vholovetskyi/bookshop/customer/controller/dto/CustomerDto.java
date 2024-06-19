package ua.vholovetskyi.bookshop.customer.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @NotBlank(message = "{order.field.isBlank}")
    private String firstName;
    @NotBlank(message = "{order.field.isBlank}")
    private String lastName;
    @Email(message = "{order.email.invalid}")
    @NotBlank(message = "{order.field.isBlank}")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "{order.phone.invalid}")
    @NotBlank(message = "{order.field.isBlank}")
    private String phone;
    @NotBlank(message = "{order.field.isBlank}")
    private String street;
    @NotBlank(message = "{order.field.isBlank}")
    private String city;
    @NotBlank(message = "{order.field.isBlank}")
    private String zipCode;
}
