package info.reinput.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
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

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("kakao_oauth2", "account_email");
        Info info = new Info()
                .title("Reinput Member")
                .version("v0.0.1")
                .description("Reinput Member Service (auth service)");
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("kakao_oauth2", kakaoOauth))
                .security(List.of(securityRequirement))
                .info(info);
    }
}
