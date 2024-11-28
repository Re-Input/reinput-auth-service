package info.reinput.member.presentation.dto.req;

import info.reinput.member.domain.SocialType;

import java.time.LocalDate;

public record SocialSignUpReq(
        String socialId,
        String name,
        LocalDate birth,
        SocialType socialType
) {
}
