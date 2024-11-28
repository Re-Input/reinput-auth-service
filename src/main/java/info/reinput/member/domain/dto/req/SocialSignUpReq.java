package info.reinput.member.domain.dto.req;

import info.reinput.member.domain.SocialType;

import java.time.LocalDate;

public record SocialSignUpReq(
        String socialId,
        String name,
        LocalDate birth,
        SocialType socialType
) {
}
