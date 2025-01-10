package info.reinput.member.presentation.dto.req;

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
}
