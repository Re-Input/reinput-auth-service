package info.reinput.member.domain.dto.req;

import info.reinput.member.domain.Job;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SignUpReq (
        @NotEmpty(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @NotNull(message = "복구 이메일은 필수입니다.")
        String recoveryEmail,
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,
        @NotEmpty(message = "비밀번호는 필수입니다.")
        String password,
        @NotEmpty(message = "이름은 필수입니다.")
        String name,
        @NotNull(message = "직업은 필수입니다.")
        Job job,
        List<String> topics
){
}
