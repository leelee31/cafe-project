package com.leesumin.cafe.point.application;

import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.exception.ErrorEnum;
import com.leesumin.cafe.exception.PointException;
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
                .orElseThrow(() -> new PointException(ErrorEnum.NOT_FOUND_POINT));
        return PointDto.builder().id(point.getId())
                .amount(point.getAmount())
                .customerId(point.getCustomer().getId())
                .build();
    }

    public Long chargePoint(Long customerId, Integer amount) {
        Point point = pointRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new PointException(ErrorEnum.NOT_FOUND_POINT));
        point.chargePoint(amount);
        pointRepository.save(point);
        return point.getId();
    }

    public Long usePoint(Long customerId, Integer amount) {
        Point point = pointRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new PointException(ErrorEnum.NOT_FOUND_POINT));
        point.usePoint(amount);
        pointRepository.save(point);
        return point.getId();
    }
}
