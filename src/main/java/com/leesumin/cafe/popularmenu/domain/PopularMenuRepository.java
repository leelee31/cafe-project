package com.leesumin.cafe.popularmenu.domain;

import com.leesumin.cafe.order.domain.OrderItem;
import com.leesumin.cafe.popularmenu.interfaces.PopularMenuDtoConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PopularMenuRepository extends JpaRepository<OrderItem, Long> {


    @Query(nativeQuery = true,
            value = "select menu_id as menuId, name as menuName, totalCount, rownum as ranking " +
                    "from (select oi.menu_id, m.name, sum(oi.counts) as totalCount " +
                    "       from order_item oi " +
                    "       left outer join orders o on oi.order_id = o.order_id " +
                    "       left outer join menu m on oi.menu_id = m.menu_id " +
                    "       where o.order_date_time between :start and :end " +
                    "       group by oi.menu_id, m.name " +
                    "       order by totalCount desc) " +
                    "where rownum <= :n")
    List<PopularMenuDtoConverter> findPopularMenu(@Param("n") int topN,
                                                  @Param("start") LocalDateTime startDateTime,
                                                  @Param("end") LocalDateTime endDateTime);
}