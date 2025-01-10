package info.reinput.member.application;


import info.reinput.member.application.command.MemberCommand;
import info.reinput.member.application.dto.MemberDto;
import info.reinput.member.domain.Member;

public interface MemberService {
    Member signUp(MemberCommand memberCommand);
    void onBoard(final MemberDto memberDto, final Long id);
}
