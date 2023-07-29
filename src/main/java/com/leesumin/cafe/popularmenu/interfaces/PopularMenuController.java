package com.leesumin.cafe.popularmenu.interfaces;

import com.leesumin.cafe.popularmenu.application.PopularMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PopularMenuController {
    private final PopularMenuService popularMenuService;

    @GetMapping("/popular/menu")
    public ResponseEntity<List<PopularMenuDto>> findPopularMenu(@RequestParam(value = "topN") int topN,
                                                      @RequestParam(value = "startDateTime") String sdt,
                                                      @RequestParam(value = "endDateTime") String edt) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime startDateTime = LocalDateTime.parse(sdt, format);
        LocalDateTime endDateTime = LocalDateTime.parse(edt, format);

        List<PopularMenuDto> menus = popularMenuService.findPopularMenu(topN, startDateTime, endDateTime);
        return ResponseEntity.ok(menus);
    }
}
