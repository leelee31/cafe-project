package com.leesumin.cafe.order.interfaces;

import com.leesumin.cafe.order.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Builder
@Data
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private String customerMobile;
    private List<OrderItemDto> orderItemDtos;
    private Integer totalPrice;
    private LocalDateTime orderDateTime;

    public OrderDto(Order order) {
        orderId = order.getId();
        customerId = order.getCustomer().getId();
        customerMobile = order.getCustomer().getMobile();
        orderItemDtos = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(toList());
        totalPrice = order.getTotalPrice();
        orderDateTime = order.getOrderDateTime();
    }
}
