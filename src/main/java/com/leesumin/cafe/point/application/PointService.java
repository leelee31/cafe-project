package com.leesumin.cafe.point.application;

import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.point.domain.Point;
import com.leesumin.cafe.point.domain.PointRepository;
import com.leesumin.cafe.point.interfaces.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRepository pointRepository;

    public Long createPoint(Long customerId, Integer amount) {
        Point point = Point.builder()
                .customer(Customer.builder().id(customerId).build())
                .amount(amount)
                .build();
        pointRepository.save(point);
        return point.getId();
    }

    public PointDto find(Long customerId) {
        Point point = pointRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("해당 고객의 포인트 정보가 없습니다."));
        return PointDto.builder().id(point.getId())
                .amount(point.getAmount())
                .customerId(point.getCustomer().getId())
                .build();
    }

    public Long chargePoint(Long customerId, Integer amount) {
        Point point = pointRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("해당 고객의 포인트 정보가 없습니다."));
        point.chargePoint(amount);
        pointRepository.save(point);
        return point.getId();
    }

    public Long usePoint(Long customerId, Integer amount) {
        Point point = pointRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("해당 고객의 포인트 정보가 없습니다."));
        point.usePoint(amount);
        pointRepository.save(point);
        return point.getId();
    }
}
