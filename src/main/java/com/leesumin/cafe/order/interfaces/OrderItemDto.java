package com.leesumin.cafe.order.interfaces;

import com.leesumin.cafe.menu.domain.Menu;
import com.leesumin.cafe.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long orderId;
    private Long menuId;
    private String menuName;
    private Integer menuPrice;
    private Integer counts;

    public OrderItemDto(OrderItem oi) {
        orderItemId = oi.getId();
        orderId = oi.getOrder().getId();
        menuId = oi.getMenu().getId();
        menuName = oi.getMenu().getName();
        menuPrice = oi.getMenu().getPrice();
        counts = oi.getCounts();
    }

    public OrderItem toEntity(Menu menu) {
        return OrderItem.builder()
                .menu(menu)
                .counts(counts)
                .build();
    }
}
