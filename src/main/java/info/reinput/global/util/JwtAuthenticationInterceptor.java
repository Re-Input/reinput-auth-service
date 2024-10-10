package info.reinput.global.util;

import info.reinput.global.domain.TokenType;
import info.reinput.global.domain.application.PrincipalDetailsService;
import info.reinput.global.domain.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private PrincipalDetailsService principalDetailsService;
    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = resolveToken(request);
        if (token != null && tokenProvider.validateToken(token)) {
            TokenType tokenType = tokenProvider.getTokenTypeByToken(token);
            if (tokenType.equals(TokenType.ACCESS)) {
                Long memberId = tokenProvider.getMemberIdFromToken(token);
                PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadMemberByMemberId(memberId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
