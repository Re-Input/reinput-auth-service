package info.reinput.member.application.impl;

import info.reinput.member.application.MemberService;
import info.reinput.member.application.command.MemberCommand;
import info.reinput.member.application.dto.FolderCreateReq;
import info.reinput.member.application.dto.MemberDto;
import info.reinput.member.application.port.out.WorkspacePort;
import info.reinput.member.domain.Member;
import info.reinput.member.infra.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRespository memberRespository;
    private final PasswordEncoder passwordEncoder;
    private final WorkspacePort workspacePort;

    @Transactional
    public Member signUp(MemberCommand memberCommand){
        log.info("singUp start");
        Member member = memberCommand.toMember(passwordEncoder);
        memberRespository.save(member);

        return member;
    }

    @Transactional
    public void onBoard(final MemberDto memberDto, final Long id){
        log.info("onBoard start");
        Member member = memberRespository.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.onBoard(memberDto.toMemberInfo());


        workspacePort.createFoldersInTopics(memberDto.getTopics().stream()
                .map(FolderCreateReq::fromDto)
                .collect(Collectors.toList()), id);

    }




}