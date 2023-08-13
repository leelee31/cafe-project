package com.leesumin.cafe.popularmenu.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PopularMenuDto {
    private Long menuId;
    private String menuName;
    private int totalCount;
    private int ranking;

    public PopularMenuDto(PopularMenuDtoConverter p) {
        menuId = p.getMenuId();
        menuName = p.getMenuName();
        totalCount = p.getTotalCount();
        ranking = p.getRanking();
    }

    @Override
    public String toString() {
        return "PopularMenuDto{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", totalCount=" + totalCount +
                ", ranking=" + ranking +
                '}';
    }
}
