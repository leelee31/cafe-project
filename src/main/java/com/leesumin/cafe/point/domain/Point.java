package com.leesumin.cafe.point.domain;

import com.leesumin.cafe.customer.domain.Customer;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "point")
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_id")
    private Long id;

    private Integer amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public void chargePoint(Integer amount) {
        checkOverflowPoint(amount);
        this.amount += amount;
    }

    public Integer usePoint(Integer amount) {
        checkEnoughPoint(amount);
        this.amount -= amount;
        return this.amount;
    }

    private void checkOverflowPoint(Integer amount) {
        if (this.amount + amount < 0)
            throw new ArithmeticException("integer overflow");
    }

    private void checkEnoughPoint(Integer amount) {
        if(this.amount - amount < 0)
            throw new IllegalArgumentException("사용할 포인트가 부족합니다.");
    }
}