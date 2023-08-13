package com.leesumin.cafe.popularmenu.application;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesumin.cafe.menu.interfaces.MenuDto;
import com.leesumin.cafe.popularmenu.domain.PopularMenuRepository;
import com.leesumin.cafe.popularmenu.interfaces.PopularMenuDto;
import com.leesumin.cafe.util.RedisUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Transactional
@RequiredArgsConstructor
@Service
public class PopularMenuService {
    private final PopularMenuRepository popularMenuRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public List<PopularMenuDto> findPopularMenuByDuration(int topN, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return popularMenuRepository.findPopularMenu(topN, startDateTime, endDateTime)
                .stream()
                .map(PopularMenuDto::new)
                .collect(toList());
    }

    public List<PopularMenuDto> findPopularMenu(int topN, LocalDateTime ldt) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        String key = RedisUtil.generateKeyYYYYMMWEEK(ldt);
        ObjectMapper ojm = new ObjectMapper();
        AtomicInteger rank = new AtomicInteger(1);
        List<PopularMenuDto> popularMenus = zSetOps.reverseRangeWithScores(key, 0, topN-1)
                .stream()
                .map(z -> {
                    MenuDto menu;
                    try {
                        menu = ojm.readValue(z.getValue().toString(), MenuDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return PopularMenuDto.builder()
                            .menuId(menu.getId())
                            .menuName(menu.getName())
                            .totalCount(z.getScore().intValue())
                            .ranking(rank.incrementAndGet())
                            .build();
                })
                .collect(toList());
        if (popularMenus == null) {
            popularMenus = popularMenuRepository.findPopularMenu(topN, ldt.minusDays(7), ldt)
                    .stream()
                    .map(PopularMenuDto::new)
                    .collect(toList());
        }
        return popularMenus;
    }
}