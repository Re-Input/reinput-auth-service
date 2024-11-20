package info.reinput.member.domain.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SignUpReq (
        @NotEmpty(message = "id는 필수 입력입니다.")
        String id,
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,
        @NotEmpty(message = "비밀번호는 필수입니다.")
        String password,
        @NotEmpty(message = "이름은 필수입니다.")
        String name,
        List<String> topics
){
}
