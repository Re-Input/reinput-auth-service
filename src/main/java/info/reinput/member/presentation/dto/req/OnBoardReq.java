package info.reinput.member.presentation.dto.req;

import info.reinput.member.application.dto.MemberDto;
import info.reinput.member.domain.Job;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record OnBoardReq(
        String email,
        String name,
        LocalDate birth,
        Job job,
        List<String> topics
){
    public static MemberDto toMemberDto(OnBoardReq onBoardReq){
        return MemberDto.builder()
                .name(onBoardReq.name())
                .birth(onBoardReq.birth())
                .job(onBoardReq.job())
                .topics(onBoardReq.topics())
                .build();
    }
}
