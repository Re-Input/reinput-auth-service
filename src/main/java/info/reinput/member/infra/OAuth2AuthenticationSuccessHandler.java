package info.reinput.member.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.reinput.global.util.TokenProvider;
import info.reinput.member.application.impl.MemberServiceImpl;
import info.reinput.member.application.CustomOAuth2Service;
import info.reinput.member.application.command.CreateSocialMemberCommand;
import info.reinput.member.domain.Member;
import info.reinput.member.domain.SocialType;
import info.reinput.member.presentation.dto.res.TokenResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomOAuth2Service customOAuth2Service;
    private final MemberServiceImpl memberService;
    private final TokenProvider tokenProvider;
    private final MemberRespository memberRepository;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(
            CustomOAuth2Service customOAuth2Service,
            @Lazy MemberServiceImpl memberService,
            TokenProvider tokenProvider,
            MemberRespository memberRepository,
            ObjectMapper objectMapper) {
        this.customOAuth2Service = customOAuth2Service;
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();

        String socialId = oauth2User.getName();

        Optional<Member> existingMember = memberRepository.findBySocialInfoSocialId(socialId);

        Member member;
        if (existingMember.isPresent()) {
            member = existingMember.get();
        } else {
            CreateSocialMemberCommand command = CreateSocialMemberCommand.builder()
                    .id(socialId)
                    .socialType(SocialType.valueOf(oauthToken.getAuthorizedClientRegistrationId().toUpperCase()))
                    .build();

            member = memberService.signUp(command);
        }

        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(tokenProvider.generateAccessToken(member.getId()))
                .refreshToken(tokenProvider.generateRefreshToken(member.getId()))
                .build();
        log.info("OAuth2AuthenticationSuccessHandler tokenResponse: {}", tokenResponse);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}