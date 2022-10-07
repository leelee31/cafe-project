package com.leesumin.cafe.order.domain;

import com.leesumin.cafe.customer.domain.Customer;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreatedDate
    private LocalDateTime orderDateTime;

    public static Order createOrder(Customer customer, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setCustomer(customer);
        for (OrderItem oi : orderItems) {
            order.addOrderItem(oi);
        }
        return order;
    }

    private void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getPriceByCounts).sum();
    }
}