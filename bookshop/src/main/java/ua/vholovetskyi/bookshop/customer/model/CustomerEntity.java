package ua.vholovetskyi.bookshop.customer.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-21
 */
@Getter
@Setter
@ToString
@Table(name = "customer")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen", sequenceName = "customer_cust_id_seq", allocationSize = 1)
    @Column(name = "cust_id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 70)
    private String firstName;
    @Column(nullable = false, length = 70)
    private String lastName;
    @Column(nullable = false, length = 70, unique = true)
    private String email;
    @Column(nullable = false, length = 16)
    private String phone;
    @Column(nullable = false, length = 80)
    private String street;
    @Column(nullable = false, length = 70)
    private String city;
    @Column(nullable = false, length = 10)
    private String zipCode;

    public boolean equalEmails(String anotherEmail) {
        return email.equalsIgnoreCase(anotherEmail);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
