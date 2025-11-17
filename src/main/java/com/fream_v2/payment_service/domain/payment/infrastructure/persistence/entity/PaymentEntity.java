package com.fream_v2.payment_service.domain.payment.infrastructure.persistence.entity;

import com.fream_v2.payment_service.domain.payment.domain.model.Payment;
import com.fream_v2.payment_service.domain.payment.domain.model.PaymentStatus;
import com.fream_v2.payment_service.domain.payment.domain.model.PaymentMethod;
import com.fream_v2.payment_service.domain.payment.domain.model.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

/**
 * 결제 엔티티 (Infrastructure Layer)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("payments")
public class PaymentEntity {
    @Id
    private Long id;
    private Long buyerId;
    private Long sellerId;
    private Long orderId;
    private String orderType;
    private Long amount;
    private String method;
    private String status;
    private String transactionId;
    private String failureReason;
    private String metadata; // JSON 문자열로 저장
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private LocalDateTime canceledAt;

    /**
     * 도메인 모델로 변환
     */
    public Payment toDomain() {
        return Payment.builder()
                .paymentId(this.id)
                .buyerId(this.buyerId)
                .sellerId(this.sellerId)
                .orderId(this.orderId)
                .orderType(OrderType.valueOf(this.orderType))
                .amount(this.amount)
                .method(PaymentMethod.valueOf(this.method))
                .status(PaymentStatus.valueOf(this.status))
                .transactionId(this.transactionId)
                .failureReason(this.failureReason)
                .metadata(parseMetadata(this.metadata))
                .requestedAt(this.requestedAt)
                .completedAt(this.completedAt)
                .canceledAt(this.canceledAt)
                .build();
    }

    /**
     * 도메인 모델로부터 엔티티 생성 (신규)
     */
    public static PaymentEntity fromDomain(Payment payment) {
        return PaymentEntity.builder()
                .buyerId(payment.getBuyerId())
                .sellerId(payment.getSellerId())
                .orderId(payment.getOrderId())
                .orderType(payment.getOrderType().name())
                .amount(payment.getAmount())
                .method(payment.getMethod().name())
                .status(payment.getStatus().name())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .metadata(toJsonString(payment.getMetadata()))
                .requestedAt(payment.getRequestedAt())
                .completedAt(payment.getCompletedAt())
                .canceledAt(payment.getCanceledAt())
                .build();
    }

    /**
     * 도메인 모델로부터 엔티티 업데이트 (기존 ID 포함)
     */
    public static PaymentEntity updateFromDomain(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getPaymentId())
                .buyerId(payment.getBuyerId())
                .sellerId(payment.getSellerId())
                .orderId(payment.getOrderId())
                .orderType(payment.getOrderType().name())
                .amount(payment.getAmount())
                .method(payment.getMethod().name())
                .status(payment.getStatus().name())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .metadata(toJsonString(payment.getMetadata()))
                .requestedAt(payment.getRequestedAt())
                .completedAt(payment.getCompletedAt())
                .canceledAt(payment.getCanceledAt())
                .build();
    }

    // Metadata JSON 변환 헬퍼 메서드
    private static String toJsonString(Object metadata) {
        // 실제 구현 시 ObjectMapper 사용
        return metadata != null ? metadata.toString() : null;
    }

    private static java.util.Map<String, Object> parseMetadata(String json) {
        // 실제 구현 시 ObjectMapper 사용
        return json != null ? new java.util.HashMap<>() : null;
    }
}