package info.reinput.member.application;

import info.reinput.member.domain.dto.req.PasswordSignUpReq;
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
    public void generalSignUp(final PasswordSignUpReq passwordSignUpReq) {
        log.info("generalSignUp start");

    }

    public void signUp(final PasswordSignUpReq passwordSignUpReq){
        log.info("singUp start");

    }


}