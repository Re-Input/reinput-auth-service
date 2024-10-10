package info.reinput.global.util;


import info.reinput.member.domain.MemberRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorityUtils {

    private static final Map<MemberRole, List<String>> ROLE_AUTHORITY_MAP = Map.of(
            MemberRole.SUPER, List.of("ROLE_SUPER", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"),
            MemberRole.ADMIN, List.of("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"),
            MemberRole.MANAGER, List.of("ROLE_MANAGER", "ROLE_USER"),
            MemberRole.USER, List.of("ROLE_USER")
    );

    public static Collection<? extends GrantedAuthority> createAuthorities(MemberRole role) {
        return ROLE_AUTHORITY_MAP.get(role).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
