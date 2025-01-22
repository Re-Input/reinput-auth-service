package info.reinput.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme kakaoOauth = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl("https://kauth.kakao.com/oauth/authorize")
                                .tokenUrl("https://kauth.kakao.com/oauth/token")
                                .scopes(new Scopes()
                                        .addString("account_email", "email")
                                )
                        )
                );
        SecurityScheme jwtAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("kakao_oauth2", "account_email")
                .addList("jwt_auth");
        Info info = new Info()
                .title("Reinput Member")
                .version("v0.0.1")
                .description("""
                  Reinput Member Service (auth service)
                  
                  인증 플로우:
                  1. 카카오 OAuth 로그인 수행
                  2. 성공 시 JWT 토큰 발급
                  3. 발급받은 JWT를 Authorization 헤더에 Bearer 형식으로 포함하여 API 호출
                  """);
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("")
                                .description("Direct"),
                        new Server()
                                .url("/member")
                                .description("Gateway")
                ))
                .components(new Components()
                        .addSecuritySchemes("kakao_oauth2", kakaoOauth)
                        .addSecuritySchemes("jwt_auth", jwtAuth))
                .security(List.of(securityRequirement))
                .info(info);
    }
}
