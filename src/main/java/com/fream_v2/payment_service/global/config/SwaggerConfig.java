package com.fream_v2.payment_service.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI 공통 설정
 *
 * WebFlux 환경에서 API 문서 자동 생성
 * 의존성: org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0
 *
 * 접근 URL:
 * - Swagger UI: http://localhost:{port}/swagger-ui.html
 * - OpenAPI Spec: http://localhost:{port}/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

    /**
     * 서비스명 (application.yml에서 주입)
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 서버 포트 (application.yml에서 주입)
     */
    @Value("${server.port}")
    private String serverPort;

    /**
     * OpenAPI 설정 빈
     *
     * Swagger UI 및 API 문서의 기본 정보 설정
     * JWT 인증 스키마 포함 (향후 Keycloak 연동 시 사용)
     */
    @Bean
    public OpenAPI openAPI() {
        // JWT 인증 스키마 이름
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // ===== API 기본 정보 =====
                .info(new Info()
                        .title(formatTitle(applicationName) + " API")  // API 제목
                        .description(formatTitle(applicationName) + " Microservice API Documentation")  // 설명
                        .version("1.0.0")  // API 버전

                        // 연락처 정보
                        .contact(new Contact()
                                .name("Fream Development Team")
                                .email("dev@fream.com")
                                .url("https://fream.com"))

                        // 라이센스 정보
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))

                // ===== 서버 정보 =====
                // Gateway를 통한 접근과 직접 접근 경로 모두 제공
                .servers(List.of(
                        // Gateway를 통한 접근 (운영 환경)
                        new Server()
                                .url("http://localhost/v1/" + extractServiceName(applicationName))
                                .description("Gateway Server (Load Balanced)"),

                        // 직접 접근 (개발/테스트 환경)
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Direct Access (Development)")
                ))

                // ===== 보안 설정 =====
                // JWT Bearer 토큰 인증 추가 (향후 Keycloak 연동)
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))

                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)  // HTTP 인증
                                .scheme("bearer")  // Bearer 토큰 방식
                                .bearerFormat("JWT")  // JWT 형식
                                .description("JWT 토큰을 입력하세요. 'Bearer' 접두사는 자동으로 추가됩니다.")));
    }

    /**
     * 서비스명을 보기 좋게 포맷팅
     * 예: user-service → User Service
     */
    private String formatTitle(String serviceName) {
        if (serviceName == null || serviceName.isEmpty()) {
            return "Fream";
        }

        // 하이픈으로 분리하고 각 단어의 첫 글자를 대문자로
        String[] parts = serviceName.split("-");
        StringBuilder formatted = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty()) {
                formatted.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return formatted.toString().trim();
    }

    /**
     * 서비스명에서 'user', 'product' 등 핵심 이름만 추출
     * 예: user-service → user
     */
    private String extractServiceName(String serviceName) {
        if (serviceName == null || serviceName.isEmpty()) {
            return "api";
        }

        // '-service' 제거
        return serviceName.replace("-service", "");
    }
}
