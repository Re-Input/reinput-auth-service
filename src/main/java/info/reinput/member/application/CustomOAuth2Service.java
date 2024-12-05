package info.reinput.member.application;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public interface CustomOAuth2Service {
    OAuth2User loadUser(OAuth2UserRequest userRequest);
}
