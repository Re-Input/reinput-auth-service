package info.reinput.member.application.command;

import info.reinput.member.domain.MemberInfo;
import info.reinput.member.domain.MemberRole;

import java.time.LocalDate;

public sealed interface MemberCommand permits CreateSocialMemberCommand, CreatePasswordMemberCommand{
    String getId();
    String getName();
    LocalDate getBirth();
    MemberRole getRole();
    boolean isEnable();
    boolean isOnboarded();

    MemberInfo toMemberInfo();
}
