package com.leesumin.cafe.point.interfaces;

import com.leesumin.cafe.point.application.PointService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PointController {
    private final PointService pointService;

    @PostMapping("/point")
    public ResponseEntity createPoint(@RequestBody PointDto point) {
        Long pointId = pointService.createPoint(point.getCustomerId(), point.getAmount());
        Map<String, Long> map = new HashMap<>();
        map.put("id", pointId);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/point/charge")
    public ResponseEntity chargePoint(@RequestBody PointDto point) {
        Long pointId = pointService.chargePoint(point.getCustomerId(), point.getAmount());
        Map<String, Long> map = new HashMap<>();
        map.put("id", pointId);
        return ResponseEntity.ok(map);
    }
}