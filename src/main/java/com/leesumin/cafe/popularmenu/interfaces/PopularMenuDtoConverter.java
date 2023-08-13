package com.leesumin.cafe.popularmenu.interfaces;

public interface PopularMenuDtoConverter {
    Long getMenuId();
    String getMenuName();
    Integer getTotalCount();
    Integer getRanking();
}