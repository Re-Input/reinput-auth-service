package info.reinput.global.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(createServers())
                .components(createSecurityComponents())
                .security(List.of(createSecurityRequirement()))
                .info(createApiInfo())
                .paths(createAuthPaths());
    }

    private List<Server> createServers() {
        return List.of(
                new Server().url("").description("Direct"),
                new Server().url("/member").description("Gateway")
        );
    }

    private Components createSecurityComponents() {
        return new Components()
                .addSecuritySchemes("kakao_oauth2", createKakaoOAuthScheme())
                .addSecuritySchemes("jwt_auth", createJwtAuthScheme());
    }

    private SecurityScheme createKakaoOAuthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .implicit(new OAuthFlow()
                                .authorizationUrl("/auth/oauth2/authorize/kakao")
                                .scopes(new Scopes()
                                        .addString("account_email", "email")
                                )
                        )
                );
    }

    private SecurityScheme createJwtAuthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
    }

    private SecurityRequirement createSecurityRequirement() {
        return new SecurityRequirement()
                .addList("kakao_oauth2", List.of("account_email"))
                .addList("jwt_auth");
    }

    private Info createApiInfo() {
        return new Info()
                .title("Reinput Member")
                .version("v0.0.1")
                .description("""
                   Reinput Member Service (auth service)
                   
                   인증 플로우:
                   1. 카카오 OAuth 로그인 (/oauth2/authorization/kakao)
                   2. 로그인 성공 시 JWT 토큰 발급
                   3. JWT 토큰을 Authorization 헤더에 Bearer 형식으로 포함하여 API 호출
                   """);
    }

    private Paths createAuthPaths() {
        return new Paths()
                .addPathItem("/auth/oauth2/authorize/kakao", createKakaoLoginPath())  // 경로 수정
                .addPathItem("/auth/oauth2/kakao/callback/v1", createKakaoCallbackPath());
    }

    private PathItem createKakaoLoginPath() {
        return new PathItem()
                .get(new Operation()
                        .tags(List.of("Authentication"))
                        .summary("카카오 소셜 로그인")
                        .description("카카오 로그인 페이지로 리다이렉트")
                        .responses(createKakaoLoginResponses())
                        .security(List.of(new SecurityRequirement().addList("kakao_oauth2"))));
    }

    private PathItem createKakaoCallbackPath() {
        return new PathItem()
                .get(new Operation()
                        .tags(List.of("Authentication"))
                        .summary("카카오 로그인 콜백")
                        .description("인증 후 JWT 토큰 발급")
                        .responses(createKakaoCallbackResponses()));
    }

    private ApiResponses createKakaoLoginResponses() {
        return new ApiResponses()
                .addApiResponse("302", new ApiResponse().description("카카오 인증 페이지로 리다이렉트"));
    }

    private ApiResponses createKakaoCallbackResponses() {
        return new ApiResponses()
                .addApiResponse("200", new ApiResponse().description("인증 성공 및 JWT 발급"));
    }
}
