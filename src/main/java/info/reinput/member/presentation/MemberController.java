package info.reinput.member.presentation;

import info.reinput.member.application.MemberService;
import info.reinput.member.domain.dto.req.SignUpReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/signup")
    public ResponseEntity<Void> signUp(final @Valid @RequestBody SignUpReq signUpReq) {
        log.info("signUpReq: {}", signUpReq);
        memberService.emailSignUp(signUpReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }





}
