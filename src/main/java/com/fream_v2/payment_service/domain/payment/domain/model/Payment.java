package com.fream_v2.payment_service.domain.payment.domain.model;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 결제 도메인 모델 (순수 도메인)
 */
@Getter
@Builder
public class Payment {
    private final Long paymentId;
    private final Long buyerId;
    private final Long sellerId;
    private final Long orderId;
    private final OrderType orderType;
    private final Long amount;
    private final PaymentMethod method;
    private final PaymentStatus status;
    private final String transactionId;
    private final String failureReason;
    private final Map<String, Object> metadata;
    private final LocalDateTime requestedAt;
    private final LocalDateTime completedAt;
    private final LocalDateTime canceledAt;

    /**
     * 결제 요청 생성
     */
    public static Payment createPaymentRequest(Long buyerId, Long sellerId, Long orderId,
                                               OrderType orderType, Long amount,
                                               PaymentMethod method) {
        return Payment.builder()
                .buyerId(buyerId)
                .sellerId(sellerId)
                .orderId(orderId)
                .orderType(orderType)
                .amount(amount)
                .method(method)
                .status(PaymentStatus.PENDING)
                .requestedAt(LocalDateTime.now())
                .build();
    }

    /**
     * 결제 처리 시작
     */
    public Payment startProcessing(String transactionId) {
        return Payment.builder()
                .paymentId(this.paymentId)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .amount(this.amount)
                .method(this.method)
                .status(PaymentStatus.PROCESSING)
                .transactionId(transactionId)
                .metadata(this.metadata)
                .requestedAt(this.requestedAt)
                .completedAt(null)
                .canceledAt(null)
                .build();
    }

    /**
     * 결제 완료 처리
     */
    public Payment completePayment(String transactionId) {
        return Payment.builder()
                .paymentId(this.paymentId)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .amount(this.amount)
                .method(this.method)
                .status(PaymentStatus.COMPLETED)
                .transactionId(transactionId)
                .metadata(this.metadata)
                .requestedAt(this.requestedAt)
                .completedAt(LocalDateTime.now())
                .canceledAt(null)
                .build();
    }

    /**
     * 결제 실패 처리
     */
    public Payment failPayment(String failureReason) {
        return Payment.builder()
                .paymentId(this.paymentId)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .amount(this.amount)
                .method(this.method)
                .status(PaymentStatus.FAILED)
                .transactionId(this.transactionId)
                .failureReason(failureReason)
                .metadata(this.metadata)
                .requestedAt(this.requestedAt)
                .completedAt(LocalDateTime.now())
                .canceledAt(null)
                .build();
    }

    /**
     * 결제 취소
     */
    public Payment cancelPayment(String reason) {
        return Payment.builder()
                .paymentId(this.paymentId)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .amount(this.amount)
                .method(this.method)
                .status(PaymentStatus.CANCELLED)
                .transactionId(this.transactionId)
                .failureReason(reason)
                .metadata(this.metadata)
                .requestedAt(this.requestedAt)
                .completedAt(this.completedAt)
                .canceledAt(LocalDateTime.now())
                .build();
    }

    /**
     * 환불 처리
     */
    public Payment refundPayment() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .amount(this.amount)
                .method(this.method)
                .status(PaymentStatus.REFUNDED)
                .transactionId(this.transactionId)
                .metadata(this.metadata)
                .requestedAt(this.requestedAt)
                .completedAt(this.completedAt)
                .canceledAt(LocalDateTime.now())
                .build();
    }

    /**
     * 상태 변경
     */
    public Payment changeStatus(PaymentStatus newStatus) {
        return Payment.builder()
                .paymentId(this.paymentId)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(this.orderType)
                .amount(this.amount)
                .method(this.method)
                .status(newStatus)
                .transactionId(this.transactionId)
                .failureReason(this.failureReason)
                .metadata(this.metadata)
                .requestedAt(this.requestedAt)
                .completedAt(this.completedAt)
                .canceledAt(this.canceledAt)
                .build();
    }

    /**
     * 결제 가능 여부 확인
     */
    public boolean isPayable() {
        return this.status == PaymentStatus.PENDING;
    }

    /**
     * 취소 가능 여부 확인
     */
    public boolean isCancellable() {
        return this.status == PaymentStatus.COMPLETED;
    }

    /**
     * 환불 가능 여부 확인
     */
    public boolean isRefundable() {
        return this.status == PaymentStatus.COMPLETED &&
                this.completedAt != null &&
                this.completedAt.isAfter(LocalDateTime.now().minusDays(7));
    }
}