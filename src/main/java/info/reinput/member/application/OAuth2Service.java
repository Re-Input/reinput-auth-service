package info.reinput.member.application;

import info.reinput.member.domain.Member;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2Service {
    OAuth2User loadUser(OAuth2UserRequest userRequest);
}
