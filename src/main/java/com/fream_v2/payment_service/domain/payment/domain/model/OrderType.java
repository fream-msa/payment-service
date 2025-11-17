package com.fream_v2.payment_service.domain.payment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 타입 값 객체
 */
@Getter
@RequiredArgsConstructor
public enum OrderType {
    SELL_ORDER("판매 주도", "판매자가 먼저 등록한 주문"),
    BUY_ORDER("구매 주도", "구매자가 먼저 등록한 주문");

    private final String name;
    private final String description;

    /**
     * 판매 주도 주문 여부
     */
    public boolean isSellOrder() {
        return this == SELL_ORDER;
    }

    /**
     * 구매 주도 주문 여부
     */
    public boolean isBuyOrder() {
        return this == BUY_ORDER;
    }
}