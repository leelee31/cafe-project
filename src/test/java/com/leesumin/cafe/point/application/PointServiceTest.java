package com.leesumin.cafe.point.application;

import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.point.domain.Point;
import com.leesumin.cafe.point.domain.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @Mock PointRepository pointRepository;
    @InjectMocks PointService pointService;

    @Test
    @DisplayName("포인트 충전")
    void chargePoint_test() {
        // given
        Integer initPoint = 0;
        Point mockPoint = Point.builder().amount(initPoint).customer(Customer.builder().id(1L).build()).build();
        given(pointRepository.findByCustomerId(any())).willReturn(Optional.ofNullable(mockPoint));

        // when
        Integer chargeAmount = 20000;
        pointService.chargePoint(1L, chargeAmount);
        Point findPoint = pointRepository.findByCustomerId(1L).orElseThrow(() -> new IllegalStateException("해당 회원 정보가 없습니다."));

        // then
        assertThat(findPoint.getAmount()).isEqualTo(initPoint + chargeAmount);
    }

    @Test
    @DisplayName("포인트 결제")
    void usePoint_test() {
        // given
        Integer initPoint = 20000;
        Point mockPoint = Point.builder().amount(initPoint).customer(Customer.builder().id(1L).build()).build();
        given(pointRepository.findByCustomerId(any())).willReturn(Optional.ofNullable(mockPoint));

        // when
        Integer useAmount = 10000;
        pointService.usePoint(1L, useAmount);
        Point findPoint = pointRepository.findByCustomerId(1L).orElseThrow(() -> new IllegalStateException("해당 회원 정보가 없습니다."));

        // then
        assertThat(findPoint.getAmount()).isEqualTo(initPoint - useAmount);
    }

}