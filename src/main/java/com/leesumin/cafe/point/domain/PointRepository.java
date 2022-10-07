package com.leesumin.cafe.point.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByCustomerId (Long customerId);
}