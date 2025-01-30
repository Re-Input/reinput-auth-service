package info.reinput.global.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getParameters().removeIf(param ->
                            param.getName().equals("X-User-Id"));
                    return operation;
                })
                .addOpenApiCustomizer(openApi -> {
                    String customJs = """
                        window.onload = function() {
                            // 스타일 추가
                            const style = document.createElement('style');
                            style.textContent = `
                                .service-nav {
                                    padding: 10px 20px;
                                    background: #f8f9fa;
                                    margin-bottom: 20px;
                                    display: flex;
                                    align-items: center;
                                }
                                .dropdown {
                                    position: relative;
                                    display: inline-block;
                                }
                                .dropbtn {
                                    background-color: #4a6bff;
                                    color: white;
                                    padding: 10px 20px;
                                    border: none;
                                    border-radius: 4px;
                                    cursor: pointer;
                                    font-size: 14px;
                                }
                                .dropdown-content {
                                    display: none;
                                    position: absolute;
                                    background-color: #f9f9f9;
                                    min-width: 200px;
                                    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                                    z-index: 1000;
                                    border-radius: 4px;
                                }
                                .dropdown-content a {
                                    color: black;
                                    padding: 12px 16px;
                                    text-decoration: none;
                                    display: block;
                                }
                                .dropdown-content a:hover {
                                    background-color: #f1f1f1;
                                }
                                .dropdown:hover .dropdown-content {
                                    display: block;
                                }
                                .dropdown:hover .dropbtn {
                                    background-color: #3d5af1;
                                }
                                .current-service {
                                    background-color: #e6e9ff !important;
                                    font-weight: bold;
                                }
                            `;
                            document.head.appendChild(style);
                            
                            // 네비게이션 바 생성
                            const nav = document.createElement('div');
                            nav.className = 'service-nav';
                            
                            // 현재 URL에서 서비스 경로 추출
                            const currentPath = window.location.pathname;
                            const currentService = currentPath.split('/')[1]; // member, insight 등
                            
                            // 드롭다운 생성
                            const dropdown = document.createElement('div');
                            dropdown.className = 'dropdown';
                            
                            const dropbtn = document.createElement('button');
                            dropbtn.className = 'dropbtn';
                            dropbtn.innerText = 'Service APIs ▼';
                            
                            const dropdownContent = document.createElement('div');
                            dropdownContent.className = 'dropdown-content';
                            
                            const services = {
                                'Auth Service': {
                                    path: '/member/swagger-ui/index.html',
                                    id: 'member'
                                },
                                'Content Service': {
                                    path: '/insight/swagger-ui/index.html',
                                    id: 'insight'
                                },
                                'Notification Service': {
                                    path: '/reminder/swagger-ui/index.html',
                                    id: 'reminder'
                                },
                                'Workspace Service': {
                                    path: '/folder/swagger-ui/index.html',
                                    id: 'folder'
                                }
                            };
                            
                            Object.entries(services).forEach(([name, service]) => {
                                const link = document.createElement('a');
                                link.href = service.path;
                                link.innerText = name;
                                
                                // 현재 서비스 하이라이트
                                if (currentPath.includes(service.id)) {
                                    link.className = 'current-service';
                                }
                                
                                dropdownContent.appendChild(link);
                            });
                            
                            dropdown.appendChild(dropbtn);
                            dropdown.appendChild(dropdownContent);
                            nav.appendChild(dropdown);
                            
                            // Swagger UI에 네비게이션 바 추가
                            const header = document.querySelector('.swagger-ui');
                            header.insertBefore(nav, header.firstChild);
                        };
                        """;
                    
                    openApi.addExtension("x-customJs", customJs);
                })
                .build();
    }

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
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl("https://kauth.kakao.com/oauth/authorize")
                                .tokenUrl("https://kauth.kakao.com/oauth/token")
                                .scopes(new Scopes()
                                        .addString("account_email", "email")
                                )
                                .extensions(Map.of(
                                        "x-client-id", kakaoClientId,
                                        "x-client-secret", kakaoClientSecret
                                ))
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
