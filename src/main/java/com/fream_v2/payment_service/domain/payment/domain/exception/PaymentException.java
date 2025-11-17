package com.fream_v2.payment_service.domain.payment.domain.exception;

import com.fream_v2.payment_service.global.presentation.exception.ErrorCode;
import com.fream_v2.payment_service.global.presentation.exception.GlobalException;

/**
 * 결제 도메인 예외
 */
public class PaymentException extends GlobalException {

    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PaymentException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public PaymentException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public PaymentException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    // ===== 자주 사용되는 예외 생성 정적 팩토리 메서드 =====

    // 조회 관련
    public static PaymentException notFound() {
        return new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND);
    }

    public static PaymentException notFound(Long paymentId) {
        return new PaymentException(PaymentErrorCode.PAYMENT_ID_NOT_FOUND,
                String.format("결제 정보를 찾을 수 없습니다. ID: %d", paymentId));
    }

    // 결제 요청 관련
    public static PaymentException invalidAmount(Long amount) {
        return new PaymentException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT,
                String.format("유효하지 않은 결제 금액입니다: %d원", amount));
    }

    public static PaymentException amountTooLow(Long amount, Long minimum) {
        return new PaymentException(PaymentErrorCode.AMOUNT_TOO_LOW,
                String.format("최소 결제 금액보다 낮습니다. 현재: %d원, 최소: %d원", amount, minimum));
    }

    public static PaymentException amountTooHigh(Long amount, Long maximum) {
        return new PaymentException(PaymentErrorCode.AMOUNT_TOO_HIGH,
                String.format("최대 결제 금액을 초과했습니다. 현재: %d원, 최대: %d원", amount, maximum));
    }

    public static PaymentException alreadyExists(Long orderId) {
        return new PaymentException(PaymentErrorCode.PAYMENT_ALREADY_EXISTS,
                String.format("이미 결제가 진행 중입니다. 주문 ID: %d", orderId));
    }

    // 결제 처리 관련
    public static PaymentException alreadyCompleted(Long paymentId) {
        return new PaymentException(PaymentErrorCode.PAYMENT_ALREADY_COMPLETED,
                String.format("이미 완료된 결제입니다. 결제 ID: %d", paymentId));
    }

    public static PaymentException insufficientBalance(Long required, Long available) {
        return new PaymentException(PaymentErrorCode.INSUFFICIENT_BALANCE,
                String.format("잔액이 부족합니다. 필요: %d원, 보유: %d원", required, available));
    }

    public static PaymentException timeout(Long paymentId) {
        return new PaymentException(PaymentErrorCode.PAYMENT_TIMEOUT,
                String.format("결제 시간이 초과되었습니다. 결제 ID: %d", paymentId));
    }

    // 카드 결제 관련
    public static PaymentException invalidCardNumber(String cardNumber) {
        return new PaymentException(PaymentErrorCode.INVALID_CARD_NUMBER,
                String.format("유효하지 않은 카드 번호입니다: %s", maskCardNumber(cardNumber)));
    }

    public static PaymentException cardExpired() {
        return new PaymentException(PaymentErrorCode.CARD_EXPIRED);
    }

    public static PaymentException cardAuthenticationFailed() {
        return new PaymentException(PaymentErrorCode.CARD_AUTHENTICATION_FAILED);
    }

    // 계좌이체 관련
    public static PaymentException invalidAccountNumber(String accountNumber) {
        return new PaymentException(PaymentErrorCode.INVALID_ACCOUNT_NUMBER,
                String.format("유효하지 않은 계좌번호입니다: %s", maskAccountNumber(accountNumber)));
    }

    public static PaymentException accountHolderMismatch() {
        return new PaymentException(PaymentErrorCode.ACCOUNT_HOLDER_MISMATCH);
    }

    // 취소/환불 관련
    public static PaymentException cancelNotAllowed(Long paymentId, String reason) {
        return new PaymentException(PaymentErrorCode.CANCEL_NOT_ALLOWED,
                String.format("취소할 수 없는 결제입니다. ID: %d, 사유: %s", paymentId, reason));
    }

    public static PaymentException cancelPeriodExpired(Long paymentId) {
        return new PaymentException(PaymentErrorCode.CANCEL_PERIOD_EXPIRED,
                String.format("취소 가능 기간이 지났습니다. 결제 ID: %d", paymentId));
    }

    public static PaymentException alreadyRefunded(Long paymentId) {
        return new PaymentException(PaymentErrorCode.ALREADY_REFUNDED,
                String.format("이미 환불된 결제입니다. 결제 ID: %d", paymentId));
    }

    public static PaymentException refundFailed(Long paymentId, String reason) {
        return new PaymentException(PaymentErrorCode.REFUND_FAILED,
                String.format("환불 처리에 실패했습니다. 결제 ID: %d, 사유: %s", paymentId, reason));
    }

    // 에스크로 관련
    public static PaymentException escrowNotAvailable() {
        return new PaymentException(PaymentErrorCode.ESCROW_NOT_AVAILABLE);
    }

    public static PaymentException escrowReleaseFailed(Long paymentId) {
        return new PaymentException(PaymentErrorCode.ESCROW_RELEASE_FAILED,
                String.format("에스크로 대금 지급에 실패했습니다. 결제 ID: %d", paymentId));
    }

    public static PaymentException escrowAlreadyReleased(Long paymentId) {
        return new PaymentException(PaymentErrorCode.ESCROW_ALREADY_RELEASED,
                String.format("이미 지급된 에스크로입니다. 결제 ID: %d", paymentId));
    }

    // 정산 관련
    public static PaymentException settlementFailed(Long sellerId) {
        return new PaymentException(PaymentErrorCode.SETTLEMENT_FAILED,
                String.format("정산 처리에 실패했습니다. 판매자 ID: %d", sellerId));
    }

    public static PaymentException minimumSettlementNotMet(Long amount, Long minimum) {
        return new PaymentException(PaymentErrorCode.MINIMUM_SETTLEMENT_NOT_MET,
                String.format("최소 정산 금액을 충족하지 못했습니다. 현재: %d원, 최소: %d원", amount, minimum));
    }

    // PG사 연동 관련
    public static PaymentException pgConnectionError(String pgName) {
        return new PaymentException(PaymentErrorCode.PG_CONNECTION_ERROR,
                String.format("PG사 연결 오류가 발생했습니다: %s", pgName));
    }

    public static PaymentException pgTimeout(String pgName) {
        return new PaymentException(PaymentErrorCode.PG_TIMEOUT,
                String.format("PG사 응답 시간이 초과되었습니다: %s", pgName));
    }

    // 헬퍼 메서드
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8) {
            return "****";
        }
        return cardNumber.substring(0, 4) + "****" + cardNumber.substring(cardNumber.length() - 4);
    }

    private static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}