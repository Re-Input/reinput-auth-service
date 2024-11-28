package info.reinput.member.application.command;

import info.reinput.member.domain.*;
import info.reinput.member.domain.dto.req.SocialSignUpReq;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateSocialMemberCommand implements MemberCommand {
    private final String id;
    private final String name;
    private final LocalDate birth;
    private final SocialType socialType;
    @Builder.Default
    private final MemberRole role = MemberRole.USER;
    @Builder.Default
    private final boolean enable =true;
    @Builder.Default
    private final boolean isOnboarded = false;

    public static CreateSocialMemberCommand from(SocialSignUpReq signUpReq){
        return CreateSocialMemberCommand.builder()
                .id(signUpReq.socialId())
                .name(signUpReq.name())
                .birth(signUpReq.birth())
                .socialType(signUpReq.socialType())
                .build();
    }

    @Override
    public Member toMember(PasswordEncoder passwordEncoder) {
        return SocialMember.builder()
                .memberInfo(toMemberInfo())
                .socialInfo(toSocialInfo())
                .role(role)
                .build();
    }

    @Override
    public MemberInfo toMemberInfo() {
        return MemberInfo.builder()
                .name(name)
                .enable(enable)
                .isOnboarded(isOnboarded)
                .build();
    }

    public SocialInfo toSocialInfo(){
        return SocialInfo.builder()
                .socialId(id)
                .socialType(socialType)
                .build();
    }
}
