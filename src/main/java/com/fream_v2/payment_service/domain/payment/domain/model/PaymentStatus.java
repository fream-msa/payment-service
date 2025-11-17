package com.fream_v2.payment_service.domain.payment.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제 상태 값 객체
 */
@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("결제 대기", "결제 대기 중"),
    PROCESSING("처리 중", "결제 처리 중"),
    COMPLETED("결제 완료", "결제가 완료되었습니다"),
    FAILED("결제 실패", "결제가 실패했습니다"),
    CANCELLED("결제 취소", "결제가 취소되었습니다"),
    REFUNDED("환불 완료", "환불이 완료되었습니다"),
    PARTIAL_REFUNDED("부분 환불", "부분 환불이 완료되었습니다");

    private final String name;
    private final String description;

    /**
     * 진행 중 상태 확인
     */
    public boolean isInProgress() {
        return this == PENDING || this == PROCESSING;
    }

    /**
     * 완료 상태 확인
     */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /**
     * 실패/취소 상태 확인
     */
    public boolean isTerminated() {
        return this == FAILED || this == CANCELLED;
    }

    /**
     * 환불 상태 확인
     */
    public boolean isRefunded() {
        return this == REFUNDED || this == PARTIAL_REFUNDED;
    }

    /**
     * 최종 상태 확인 (더 이상 변경 불가)
     */
    public boolean isFinal() {
        return this == COMPLETED || this == FAILED ||
                this == CANCELLED || this == REFUNDED || this == PARTIAL_REFUNDED;
    }

    /**
     * 취소 가능 여부
     */
    public boolean isCancellable() {
        return this == COMPLETED;
    }

    /**
     * 환불 가능 여부
     */
    public boolean isRefundable() {
        return this == COMPLETED;
    }
}