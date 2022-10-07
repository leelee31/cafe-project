package com.leesumin.cafe.order.domain;

import com.leesumin.cafe.menu.domain.Menu;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name ="order_item")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer counts;

    @CreatedDate
    private LocalDateTime orderItemDateTime;

    @Builder
    public OrderItem(Menu menu, Integer counts) {
        this.menu = menu;
        this.counts = counts;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getPriceByCounts() {
        return counts * menu.getPrice();
    }
}