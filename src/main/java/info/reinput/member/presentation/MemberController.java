package info.reinput.member.presentation;

import info.reinput.member.application.impl.MemberServiceImpl;
import info.reinput.member.presentation.dto.req.OnBoardReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberServiceImpl memberService;

    @Operation(
            summary = "onBoardingV1",
            description = "onBoarding 정보를 저장하고 폴더를 생성합니다."
    )
    @PostMapping("/onboarding/v1")
    public ResponseEntity<Void> onBoardingV1(
            @RequestBody final OnBoardReq onBoardReq,
            @RequestHeader("X-User-Id") final Long userId){
        log.info("onBoardingV1 start");
        memberService.onBoard(OnBoardReq.toMemberDto(onBoardReq), userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
