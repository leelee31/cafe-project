package com.leesumin.cafe.popularmenu.application;

import com.leesumin.cafe.popularmenu.domain.PopularMenuRepository;
import com.leesumin.cafe.popularmenu.interfaces.PopularMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PopularMenuService {
    private final PopularMenuRepository popularMenuRepository;

    public List<PopularMenuDto> findPopularMenu(int topN, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return popularMenuRepository.findPopularMenu(topN, startDateTime, endDateTime);
    }
}
