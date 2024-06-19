package ua.vholovetskyi.bookshop.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-21
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);
}
