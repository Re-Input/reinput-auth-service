package info.reinput.member.application.command;

import info.reinput.member.domain.MemberInfo;
import info.reinput.member.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public final class CreatePasswordMemberCommand implements MemberCommand{
    private final String id;
    private final String name;
    private final String password;
    private final LocalDate birth;
    @Builder.Default
    private final MemberRole role = MemberRole.USER;
    @Builder.Default
    private final boolean enable =true;
    @Builder.Default
    private final boolean isOnboarded = false;

    public static CreatePasswordMemberCommand from(String id, String name, String password, LocalDate birth){
        return CreatePasswordMemberCommand.builder()
                .id(id)
                .name(name)
                .password(password)
                .birth(birth)
                .build();
    }

    public static CreatePasswordMemberCommand fromAdmin(String id, String name, String password, LocalDate birth){
        return CreatePasswordMemberCommand.builder()
                .id(id)
                .name(name)
                .password(password)
                .birth(birth)
                .role(MemberRole.ADMIN)
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


}
