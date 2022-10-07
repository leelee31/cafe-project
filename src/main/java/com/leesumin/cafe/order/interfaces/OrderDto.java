package com.leesumin.cafe.order.interfaces;

import com.leesumin.cafe.order.domain.Order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private String customerMobile;
    private List<OrderItemDto> orderItems;
    private Integer totalPrice;
    private LocalDateTime orderDateTime;

    public static OrderDto OrderToDto (Order order){
       return OrderDto.builder()
               .orderId(order.getId())
               .customerId(order.getCustomer().getId())
               .customerMobile(order.getCustomer().getMobile())
               .orderItems(order.getOrderItems().stream().map(
                       orderItem -> {
                           return OrderItemDto.builder()
                                   .menuId(orderItem.getMenu().getId())
                                   .menuName(orderItem.getMenu().getName())
                                   .counts(orderItem.getCounts())
                                   .build();
                       }
               ).collect(Collectors.toList()))
               .totalPrice(order.getTotalPrice())
               .orderDateTime(order.getOrderDateTime())
               .build();
    }
}
