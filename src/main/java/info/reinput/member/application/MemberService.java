package info.reinput.member.application;

import info.reinput.member.application.command.MemberCommand;
import info.reinput.member.domain.Member;
import info.reinput.member.infra.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRespository memberRespository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberCommand memberCommand){
        log.info("singUp start");
        Member member = memberCommand.toMember(passwordEncoder);
        memberRespository.save(member);
    }


}