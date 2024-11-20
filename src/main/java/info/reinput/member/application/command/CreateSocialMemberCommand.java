package info.reinput.member.application.command;

import info.reinput.member.domain.SocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateSocialMemberCommand {
    private final String name;
    private final String socialId;
    private final String profileImage;
    private final SocialType socialType;
    private final LocalDate birth;


    public static CreateSocialMemberCommand of(String name, String socialId, String profileImage, SocialType socialType, LocalDate birth) {
        return CreateSocialMemberCommand.builder()
                .name(name)
                .socialId(socialId)
                .profileImage(profileImage)
                .socialType(socialType)
                .birth(birth)
                .build();
    }
}
