package com.leesumin.cafe.customer.application;

import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.customer.interfaces.CustomerDto;

import com.leesumin.cafe.exception.CustomerException;
import com.leesumin.cafe.exception.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Long createCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .mobile(customerDto.getMobile())
                .build();
        customerRepository.save(customer);
        return customer.getId();
    }

    public CustomerDto findByMobile(String mobile) {
        Customer customer = customerRepository.findByMobile(mobile)
                .orElseThrow(() -> new CustomerException(ErrorEnum.NOT_FOUND_CUSTOMER));
        return CustomerDto.builder()
                .id(customer.getId())
                .mobile(customer.getMobile())
                .build();
    }
}