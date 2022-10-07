package com.leesumin.cafe.popularmenu.domain;

import com.leesumin.cafe.order.domain.OrderItem;
import com.leesumin.cafe.popularmenu.interfaces.PopularMenuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PopularMenuRepository extends JpaRepository<OrderItem, Long> {

    @Query(nativeQuery = true,
            value = "select menu_id as menuId, totalCount, rownum as ranking " +
                    "from (select menu_id, sum(counts) as totalCount " +
                    "      from order_item " +
                    "      where order_item_date_time between :startDt and :endDt " +
                    "      group by menu_id " +
                    "      order by totalCount desc)" +
                    "where rownum <= :n")
    List<PopularMenuDto> findPopularMenu(@Param("n") int topN,
                                           @Param("startDt") LocalDateTime startDateTime,
                                           @Param("endDt") LocalDateTime endDateTime);
}