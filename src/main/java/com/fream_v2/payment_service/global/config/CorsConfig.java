package com.fream_v2.payment_service.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS (Cross-Origin Resource Sharing) 설정
 *
 * WebFlux 환경에서 CORS 허용 설정
 * 프론트엔드에서 API 호출을 허용하기 위한 설정
 *
 * 현재: 모든 Origin 허용 (개발 환경)
 * 운영: 특정 도메인만 허용하도록 변경 필요
 */
@Configuration
public class CorsConfig {

    /**
     * CORS WebFilter 설정
     *
     * WebFlux는 CorsWebFilter를 사용하여 CORS 처리
     * MVC의 @CrossOrigin과 달리 Filter 방식 사용
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        // CORS 설정 객체 생성
        CorsConfiguration config = new CorsConfiguration();

        // ===== Credentials 설정 =====
        // 쿠키, 인증 헤더 등을 포함한 요청 허용
        config.setAllowCredentials(true);

        // ===== Origin 설정 =====
        // 모든 Origin 허용 (개발 환경)
        config.addAllowedOriginPattern("*");

        /* ===== 운영 환경 설정 예시 =====
        // 특정 도메인만 허용
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",  // 로컬 프론트엔드
                "https://www.fream.com",  // 운영 도메인
                "https://admin.fream.com"  // 관리자 도메인
        ));
        */

        // ===== Headers 설정 =====
        // 모든 헤더 허용
        config.setAllowedHeaders(List.of("*"));

        /* ===== 운영 환경 설정 예시 =====
        // 필요한 헤더만 허용
        config.setAllowedHeaders(Arrays.asList(
                "Authorization",  // JWT 토큰
                "Content-Type",  // 요청 타입
                "Accept",  // 응답 타입
                "X-Requested-With",  // AJAX 요청
                "X-Custom-Header"  // 커스텀 헤더
        ));
        */

        // ===== HTTP Methods 설정 =====
        // 모든 HTTP 메서드 허용
        config.setAllowedMethods(Arrays.asList(
                "GET",     // 조회
                "POST",    // 생성
                "PUT",     // 전체 수정
                "PATCH",   // 부분 수정
                "DELETE",  // 삭제
                "OPTIONS"  // Preflight 요청
        ));

        // ===== Exposed Headers 설정 =====
        // 브라우저에서 접근 가능한 응답 헤더 지정
        config.setExposedHeaders(Arrays.asList(
                "Authorization",  // JWT 토큰
                "X-Total-Count",  // 페이징 총 개수
                "X-Page-Number",  // 현재 페이지
                "X-Page-Size"     // 페이지 크기
        ));

        // ===== Max Age 설정 =====
        // Preflight 요청 캐시 시간 (초)
        // 브라우저가 이 시간 동안 Preflight 요청 결과를 캐시
        config.setMaxAge(3600L);  // 1시간

        // ===== URL 패턴 매핑 =====
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 경로에 대해 위 CORS 설정 적용
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
