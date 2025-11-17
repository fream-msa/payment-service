package com.fream_v2.payment_service.domain.payment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제 수단 값 객체
 */
@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CARD("카드", "신용/체크카드"),
    BANK_TRANSFER("계좌이체", "은행 계좌이체"),
    ESCROW("에스크로", "에스크로 결제"),
    VIRTUAL_ACCOUNT("가상계좌", "가상계좌 입금"),
    MOBILE_PAYMENT("모바일결제", "모바일 간편결제");

    private final String name;
    private final String description;

    /**
     * 즉시 결제 수단 여부
     */
    public boolean isInstantPayment() {
        return this == CARD || this == MOBILE_PAYMENT;
    }

    /**
     * 입금 대기 필요 여부
     */
    public boolean requiresDepositWaiting() {
        return this == VIRTUAL_ACCOUNT || this == BANK_TRANSFER;
    }

    /**
     * 에스크로 결제 여부
     */
    public boolean isEscrow() {
        return this == ESCROW;
    }
}