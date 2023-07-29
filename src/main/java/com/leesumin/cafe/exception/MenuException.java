package com.leesumin.cafe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuException extends BusinessException {
    private final Logger logger = LoggerFactory.getLogger(MenuException.class);

    public MenuException(ErrorEnum error) {
        super(error);
    }

    public MenuException(ErrorEnum error, Long menuId) {
        super(error);
        logger.error("메뉴 id: " + menuId);
    }
}
