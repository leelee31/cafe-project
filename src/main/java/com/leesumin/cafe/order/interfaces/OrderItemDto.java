package com.leesumin.cafe.order.interfaces;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderItemDto {
    private final Long orderItemId;
    private final Long orderId;
    private final Long menuId;
    private final String menuName;
    private final Integer menuPrice;
    private final Integer counts;
}