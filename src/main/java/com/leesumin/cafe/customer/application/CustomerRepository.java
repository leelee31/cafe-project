package com.leesumin.cafe.customer.application;

import com.leesumin.cafe.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByMobile(String mobile);
}
