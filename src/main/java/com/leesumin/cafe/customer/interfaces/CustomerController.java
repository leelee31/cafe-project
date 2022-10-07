package com.leesumin.cafe.customer.interfaces;

import com.leesumin.cafe.customer.application.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customer/{mobile}")
    public ResponseEntity<CustomerDto> findByMobile(@PathVariable String mobile) {
        CustomerDto customerDto = customerService.findByMobile(mobile);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/customer")
    public ResponseEntity createCustomer(@RequestBody CustomerDto customerDto) {
        Long customerId = customerService.createCustomer(customerDto);
        Map<String, Long> map = new HashMap<>();
        map.put("id", customerId);
        return ResponseEntity.ok(map);
    }

}