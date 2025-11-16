package com.fream_v2.payment_service.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Spring Security 공통 설정 (WebFlux 환경)
 *
 * 현재 설정: 모든 요청 허용 (개발 단계)
 * 향후 변경: Keycloak OAuth2/JWT 인증 적용
 *
 * WebFlux Security는 ServerHttpSecurity를 사용
 * MVC의 HttpSecurity와 다른 방식으로 동작
 */
@Configuration
@EnableWebFluxSecurity  // WebFlux Security 활성화
@EnableReactiveMethodSecurity  // @PreAuthorize, @PostAuthorize 등 메서드 보안 활성화
public class SecurityConfig {

    /**
     * Security Filter Chain 설정
     *
     * 현재: 모든 엔드포인트 허용 (개발/테스트용)
     *
     * 향후 Keycloak 적용 시:
     * - /actuator/** : 모니터링 엔드포인트 (인증 불필요)
     * - /swagger-ui/** : API 문서 (인증 불필요)
     * - /v3/api-docs/** : OpenAPI 스펙 (인증 불필요)
     * - /api/** : 비즈니스 API (JWT 인증 필요)
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                // ===== CSRF 보호 비활성화 =====
                // REST API는 stateless하므로 CSRF 불필요
                // JWT 토큰 기반 인증 사용 시 CSRF 공격 위험 낮음
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // ===== CORS 설정 =====
                // CORS는 CorsConfig에서 별도 관리
                .cors(cors -> cors.disable())  // 기본 CORS 비활성화 (CorsWebFilter 사용)

                // ===== 인증/인가 규칙 설정 =====
                .authorizeExchange(exchange -> exchange
                                // 모든 요청 허용 (개발 단계)
                                .anyExchange().permitAll()

                        /* ===== 향후 Keycloak 적용 시 사용할 설정 =====

                        // Public 엔드포인트 (인증 불필요)
                        .pathMatchers("/actuator/**").permitAll()  // Actuator 엔드포인트
                        .pathMatchers("/swagger-ui/**").permitAll()  // Swagger UI
                        .pathMatchers("/swagger-ui.html").permitAll()  // Swagger UI 메인
                        .pathMatchers("/v3/api-docs/**").permitAll()  // OpenAPI 문서
                        .pathMatchers("/webjars/**").permitAll()  // Swagger 리소스
                        .pathMatchers("/health").permitAll()  // Health Check
                        .pathMatchers("/favicon.ico").permitAll()  // Favicon

                        // 인증이 필요한 API 엔드포인트
                        .pathMatchers("/api/**").authenticated()  // 모든 비즈니스 API

                        // 그 외 모든 요청은 인증 필요
                        .anyExchange().authenticated()
                        */
                )

                /* ===== 향후 Keycloak OAuth2/JWT 적용 시 사용할 설정 =====

                // OAuth2 Resource Server 설정 (JWT 검증)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                // JWT 인증 컨버터 (권한 추출)
                                .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter())
                        )
                )
                */

                .build();
    }

    /* ===== 향후 사용할 추가 Bean =====

    /**
     * Password Encoder (사용자 비밀번호 암호화)
     * Keycloak 사용 시 필요없지만, 로컬 개발용 사용자 생성 시 필요할 수 있음
     *
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Reactive User Details Service (사용자 정보 조회)
     * Keycloak 사용 시 필요없음
     *
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user);
    }
    */
}
