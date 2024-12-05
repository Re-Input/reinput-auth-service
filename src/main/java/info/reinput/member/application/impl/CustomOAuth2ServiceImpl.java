package info.reinput.member.application.impl;

import info.reinput.member.application.CustomOAuth2Service;
import info.reinput.member.application.command.CreateSocialMemberCommand;
import info.reinput.member.domain.Member;
import info.reinput.member.domain.SocialType;
import info.reinput.member.infra.MemberRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2ServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2Service {
    private final MemberRespository memberRespository;


    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OAuth2User load start");
        OAuth2User oAuth2User= super.loadUser(userRequest);
        try{
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception e) {
            log.error("OAuth2User load error", e);
            throw new OAuth2AuthenticationException(e.getMessage());
        }

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        log.info("processOAuth2User start");
        SocialType socialType = getSocialType(userRequest.getClientRegistration().getRegistrationId());
        String socialId = extractEmail(socialType, oAuth2User);

        Optional<Member> memberOptional =  memberRespository.findBySocialInfoSocialId(socialId);
        Member member = memberOptional.orElseGet(() -> createSocialMember(socialId, socialType));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                oAuth2User.getAttributes(),
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName()
        );
    }

    private String extractEmail(SocialType socialType, OAuth2User oAuth2User) {
        //todo : custom exception
        return switch (socialType) {
            case GOOGLE -> oAuth2User.getAttribute("email");
            case KAKAO -> {
                var kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
                yield kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
            }
            default -> throw new IllegalArgumentException("지원하지 않는 소셜입니다.");
        };
    }

    private SocialType getSocialType(String registrationId) {
        return SocialType.valueOf(registrationId.toUpperCase());
    }

    private Member createSocialMember(String socialId, SocialType socialType){
        log.info("createSocialMember create new Member : {}", socialId);
        CreateSocialMemberCommand command = CreateSocialMemberCommand.builder()
                .id(socialId)
                .socialType(socialType)
                .build();
        //refactor
        return memberRespository.save(command.toMember(null));
    }
}
