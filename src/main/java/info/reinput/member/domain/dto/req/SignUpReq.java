package info.reinput.member.domain.dto.req;

import info.reinput.member.domain.Job;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SignUpReq (
        @NotEmpty(message = "id는 필수 입력입니다.")
        String id,
        @NotNull(message = "복구 이메일은 필수입니다.")
        String recoveryEmail,
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,
        @NotEmpty(message = "비밀번호는 필수입니다.")
        String password,
        @NotEmpty(message = "이름은 필수입니다.")
        String name,
        @NotNull(message = "mail 코드가 필요합니다.")
        String mailCode,
        List<String> topics
){
}
