package com.leesumin.cafe.point.interfaces;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PointDto {
    private final Long id;
    private final Long customerId;
    private final Integer amount;
}