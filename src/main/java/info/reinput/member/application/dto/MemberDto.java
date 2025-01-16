package info.reinput.member.application.dto;

import info.reinput.member.domain.Job;
import info.reinput.member.domain.MemberInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class MemberDto {
    private Long id;
    private String name;
    private LocalDate birth;
    private Job job;
    private List<String> topics;


    public MemberInfo toMemberInfo(){
        return MemberInfo.builder()
                .name(name)
                .birth(birth)
                .job(job)
                .topics(topics)
                .build();
    }
}
