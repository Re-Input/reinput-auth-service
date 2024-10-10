package info.reinput.global.domain.application;

import info.reinput.global.domain.auth.PrincipalDetails;
import info.reinput.member.domain.Member;
import info.reinput.member.infra.MemberRespository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService {
    private final MemberRespository memberRespository;
    /*
        UserDetailsService 구현체
        memberId가 존재 할경우 PrincipalDetails로 반환
     */
    public UserDetails loadMemberByMemberId(Long memberId) throws UsernameNotFoundException {
        Member member = memberRespository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다. : " + memberId));

        log.info("[PrincipalDetailsService] memberId : {}", member.getId());
        return new PrincipalDetails(member);
    }

    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long memberId = Long.parseLong(username);
        Member member = memberRespository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        log.info("[PrincipalDetailsService] memberId : {}", member.getId());
        return new PrincipalDetails(member);
    }*/


}
