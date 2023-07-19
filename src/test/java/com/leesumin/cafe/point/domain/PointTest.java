package com.leesumin.cafe.point.domain;

import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.exception.NotEnoughPointException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void createPoint_test() {
        Point point = Point.builder()
                .customer(Customer.builder().id(12345L).build())
                .amount(0)
                .build();

        assertAll(
                () -> assertNotNull(point),
                () -> assertEquals(Long.valueOf(12345), point.getCustomer().getId())
        );
    }

    @Test
    @DisplayName("충전 정상")
    void chargePoint_test() {
        Point point = Point.builder()
                .customer(Customer.builder().id(12345L).build())
                .amount(0)
                .build();

        point.chargePoint(10000);

        assertEquals(10000, point.getAmount());
    }

    @Test
    void usePoint_test() {
        Point point = Point.builder()
                .customer(Customer.builder().id(12345L).build())
                .amount(500)
                .build();

        point.usePoint(400);

        assertEquals(100, point.getAmount());
    }

    @Test
    @DisplayName("적립할 포인트 overflow")
    void chargePoint_overflow_test() {
        Point point = Point.builder()
                .customer(Customer.builder().id(12345L).build())
                .amount(0)
                .build();

        assertThrows(ArithmeticException.class, () -> point.chargePoint(Integer.MAX_VALUE + 1));
    }

    @Test
    @DisplayName("사용할 포인트 부족")
    void usePoint_notEnough_test() {
        Point point = Point.builder()
                .customer(Customer.builder().id(12345L).build())
                .amount(100)
                .build();

        assertThrows(NotEnoughPointException.class, () -> point.usePoint(100000));
    }
}