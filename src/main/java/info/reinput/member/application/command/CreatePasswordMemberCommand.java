package info.reinput.member.application.command;

import info.reinput.member.domain.Job;
import info.reinput.member.domain.MemberRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePasswordMemberCommand {
    private final String name;
    private final String nickname;
    private final Job job;
    private final String password;
    @Builder.Default
    private final List<String> topics = new ArrayList<>();
    @Builder.Default
    private final MemberRole role = MemberRole.USER;

    public static CreatePasswordMemberCommandBuilder of(String name, String nickname, Job job, String password) {
        return CreatePasswordMemberCommand.builder()
                .name(name)
                .nickname(nickname)
                .job(job)
                .password(password);
    }
}
