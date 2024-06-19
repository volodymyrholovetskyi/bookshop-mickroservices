package ua.vholovetskyi.bookshop.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vholovetskyi.bookshop.commons.exception.impl.customer.CustomerNotFoundException;
import ua.vholovetskyi.bookshop.commons.exception.impl.customer.EmailAlreadyExistsException;
import ua.vholovetskyi.bookshop.commons.publisher.NotificationPublisher;
import ua.vholovetskyi.bookshop.customer.model.CustomerEntity;
import ua.vholovetskyi.bookshop.customer.repository.CustomerRepository;

import java.util.List;

import static ua.vholovetskyi.bookshop.customer.mapper.CustomerNotificationFactory.createNotification;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final NotificationPublisher notificationPublisher;
    private final CustomerRepository customerRepo;

    public List<CustomerEntity> findAll() {
        return customerRepo.findAll();
    }

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customer) {
        validateCustomerEmailExists(customer);
        var savedCustomer = customerRepo.save(customer);
        notificationPublisher.publishNotification(createNotification(savedCustomer));
        return savedCustomer;
    }

    @Transactional
    public CustomerEntity updateCustomer(CustomerEntity customer) {
        return customerRepo.findById(customer.getId())
                .map(loadCustomer -> {
                    validateCustomerEmailExists(loadCustomer, customer);
                    return customerRepo.save(customer);
                }).orElseThrow(() -> new CustomerNotFoundException(customer.getId()));
    }

    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    private void validateCustomerEmailExists(CustomerEntity customer) {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new EmailAlreadyExistsException(customer.getEmail());
        }
    }

    private void validateCustomerEmailExists(CustomerEntity loadCustomer, CustomerEntity customer) {
        if (customerRepo.existsByEmail(customer.getEmail()) && !loadCustomer.equalEmails(customer.getEmail())) {
            throw new EmailAlreadyExistsException(customer.getEmail());
        }
    }
}
