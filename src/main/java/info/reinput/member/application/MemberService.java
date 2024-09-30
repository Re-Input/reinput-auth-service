package info.reinput.member.application;

import info.reinput.member.domain.Member;
import info.reinput.member.domain.dto.req.SignUpReq;
import info.reinput.member.infra.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRespository memberRespository;

    @Transactional
    public void signUp(final SignUpReq signUpReq) {
        log.info("signUpReq email: {}", signUpReq.email());

        Member member = Member.signUp(signUpReq);

    }


}