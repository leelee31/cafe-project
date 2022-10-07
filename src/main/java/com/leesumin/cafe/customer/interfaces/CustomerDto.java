package com.leesumin.cafe.customer.interfaces;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {
    private final Long id;
    private final String mobile;
}