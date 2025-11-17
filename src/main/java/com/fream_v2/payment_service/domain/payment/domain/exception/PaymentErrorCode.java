package com.fream_v2.payment_service.domain.payment.domain.exception;

import com.fream_v2.payment_service.global.presentation.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제 도메인 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    // ===== 결제 조회 관련 (PAY_001 ~ PAY_099) =====
    PAYMENT_NOT_FOUND("PAY_001", "결제 정보를 찾을 수 없습니다.", 404),
    PAYMENT_ID_NOT_FOUND("PAY_002", "해당 ID의 결제 정보를 찾을 수 없습니다.", 404),
    TRANSACTION_NOT_FOUND("PAY_003", "거래 정보를 찾을 수 없습니다.", 404),
    PAYMENT_DATA_CORRUPTED("PAY_004", "결제 데이터가 손상되었습니다.", 500),

    // ===== 결제 요청 관련 (PAY_100 ~ PAY_199) =====
    INVALID_PAYMENT_AMOUNT("PAY_100", "유효하지 않은 결제 금액입니다.", 400),
    AMOUNT_TOO_LOW("PAY_101", "최소 결제 금액보다 낮습니다.", 400),
    AMOUNT_TOO_HIGH("PAY_102", "최대 결제 금액을 초과했습니다.", 400),
    INVALID_PAYMENT_METHOD("PAY_103", "지원하지 않는 결제 수단입니다.", 400),
    PAYMENT_ALREADY_EXISTS("PAY_104", "이미 결제가 진행 중입니다.", 409),
    PAYMENT_REQUEST_FAILED("PAY_105", "결제 요청에 실패했습니다.", 500),
    INVALID_ORDER_INFO("PAY_106", "주문 정보가 유효하지 않습니다.", 400),
    BUYER_SELLER_SAME("PAY_107", "구매자와 판매자가 동일합니다.", 400),
    PAYMENT_INFO_MISSING("PAY_108", "필수 결제 정보가 누락되었습니다.", 400),
    DUPLICATE_PAYMENT_REQUEST("PAY_109", "중복된 결제 요청입니다.", 409),

    // ===== 결제 처리 관련 (PAY_200 ~ PAY_299) =====
    PAYMENT_PROCESSING_FAILED("PAY_200", "결제 처리에 실패했습니다.", 500),
    PAYMENT_ALREADY_COMPLETED("PAY_201", "이미 완료된 결제입니다.", 409),
    PAYMENT_ALREADY_CANCELLED("PAY_202", "이미 취소된 결제입니다.", 409),
    PAYMENT_TIMEOUT("PAY_203", "결제 시간이 초과되었습니다.", 408),
    INSUFFICIENT_BALANCE("PAY_204", "잔액이 부족합니다.", 400),
    PAYMENT_LIMIT_EXCEEDED("PAY_205", "결제 한도를 초과했습니다.", 400),
    PAYMENT_VERIFICATION_FAILED("PAY_206", "결제 검증에 실패했습니다.", 400),
    INVALID_PAYMENT_STATUS("PAY_207", "유효하지 않은 결제 상태입니다.", 400),
    PAYMENT_IN_PROGRESS("PAY_208", "결제가 진행 중입니다.", 409),

    // ===== 카드 결제 관련 (PAY_300 ~ PAY_349) =====
    INVALID_CARD_NUMBER("PAY_300", "유효하지 않은 카드 번호입니다.", 400),
    INVALID_CARD_CVV("PAY_301", "유효하지 않은 CVV입니다.", 400),
    CARD_EXPIRED("PAY_302", "만료된 카드입니다.", 400),
    CARD_AUTHENTICATION_FAILED("PAY_303", "카드 인증에 실패했습니다.", 401),
    CARD_LIMIT_EXCEEDED("PAY_304", "카드 한도를 초과했습니다.", 400),
    CARD_BLOCKED("PAY_305", "차단된 카드입니다.", 400),
    CARD_NOT_SUPPORTED("PAY_306", "지원하지 않는 카드입니다.", 400),

    // ===== 계좌이체 관련 (PAY_350 ~ PAY_399) =====
    INVALID_ACCOUNT_NUMBER("PAY_350", "유효하지 않은 계좌번호입니다.", 400),
    INVALID_BANK_CODE("PAY_351", "유효하지 않은 은행 코드입니다.", 400),
    ACCOUNT_HOLDER_MISMATCH("PAY_352", "예금주가 일치하지 않습니다.", 400),
    TRANSFER_LIMIT_EXCEEDED("PAY_353", "이체 한도를 초과했습니다.", 400),
    ACCOUNT_BLOCKED("PAY_354", "거래 제한 계좌입니다.", 400),

    // ===== 취소/환불 관련 (PAY_400 ~ PAY_499) =====
    CANCEL_NOT_ALLOWED("PAY_400", "취소할 수 없는 결제입니다.", 400),
    CANCEL_PERIOD_EXPIRED("PAY_401", "취소 가능 기간이 지났습니다.", 400),
    PARTIAL_CANCEL_NOT_ALLOWED("PAY_402", "부분 취소가 불가능합니다.", 400),
    CANCEL_AMOUNT_EXCEEDED("PAY_403", "취소 금액이 결제 금액을 초과합니다.", 400),
    CANCELLATION_FAILED("PAY_404", "결제 취소에 실패했습니다.", 500),
    REFUND_NOT_AVAILABLE("PAY_405", "환불이 불가능한 결제입니다.", 400),
    REFUND_PERIOD_EXPIRED("PAY_406", "환불 가능 기간이 지났습니다.", 400),
    ALREADY_REFUNDED("PAY_407", "이미 환불된 결제입니다.", 409),
    REFUND_FAILED("PAY_408", "환불 처리에 실패했습니다.", 500),
    REFUND_ACCOUNT_INVALID("PAY_409", "환불 계좌 정보가 유효하지 않습니다.", 400),

    // ===== 에스크로 관련 (PAY_500 ~ PAY_599) =====
    ESCROW_NOT_AVAILABLE("PAY_500", "에스크로 서비스를 사용할 수 없습니다.", 400),
    ESCROW_REGISTRATION_FAILED("PAY_501", "에스크로 등록에 실패했습니다.", 500),
    ESCROW_RELEASE_FAILED("PAY_502", "에스크로 대금 지급에 실패했습니다.", 500),
    ESCROW_HOLD_FAILED("PAY_503", "에스크로 보관에 실패했습니다.", 500),
    ESCROW_NOT_CONFIRMED("PAY_504", "구매 확정이 되지 않았습니다.", 400),
    ESCROW_ALREADY_RELEASED("PAY_505", "이미 지급된 에스크로입니다.", 409),

    // ===== 정산 관련 (PAY_600 ~ PAY_699) =====
    SETTLEMENT_NOT_AVAILABLE("PAY_600", "정산이 불가능합니다.", 400),
    SETTLEMENT_AMOUNT_MISMATCH("PAY_601", "정산 금액이 일치하지 않습니다.", 400),
    SETTLEMENT_FAILED("PAY_602", "정산 처리에 실패했습니다.", 500),
    SETTLEMENT_ALREADY_PROCESSED("PAY_603", "이미 정산 처리되었습니다.", 409),
    MINIMUM_SETTLEMENT_NOT_MET("PAY_604", "최소 정산 금액을 충족하지 못했습니다.", 400),
    SETTLEMENT_ACCOUNT_INVALID("PAY_605", "정산 계좌 정보가 유효하지 않습니다.", 400),

    // ===== PG사 연동 관련 (PAY_700 ~ PAY_799) =====
    PG_CONNECTION_ERROR("PAY_700", "PG사 연결 오류가 발생했습니다.", 502),
    PG_AUTHENTICATION_FAILED("PAY_701", "PG사 인증에 실패했습니다.", 401),
    PG_RESPONSE_ERROR("PAY_702", "PG사 응답 오류가 발생했습니다.", 502),
    PG_TIMEOUT("PAY_703", "PG사 응답 시간이 초과되었습니다.", 504),
    PG_SERVICE_UNAVAILABLE("PAY_704", "PG 서비스를 일시적으로 사용할 수 없습니다.", 503),
    INVALID_PG_RESPONSE("PAY_705", "PG사 응답이 유효하지 않습니다.", 502),

    // ===== 검증 관련 (PAY_800 ~ PAY_899) =====
    PAYMENT_DATA_INVALID("PAY_800", "결제 데이터가 유효하지 않습니다.", 400),
    REQUIRED_FIELD_MISSING("PAY_801", "필수 입력 항목이 누락되었습니다.", 400),
    INVALID_BUYER_INFO("PAY_802", "구매자 정보가 유효하지 않습니다.", 400),
    INVALID_SELLER_INFO("PAY_803", "판매자 정보가 유효하지 않습니다.", 400),
    AMOUNT_MISMATCH("PAY_804", "결제 금액이 일치하지 않습니다.", 400),
    SIGNATURE_VERIFICATION_FAILED("PAY_805", "서명 검증에 실패했습니다.", 400);

    private final String code;
    private final String message;
    private final int status;
}