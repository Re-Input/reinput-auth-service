package info.reinput.member.presentation.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PasswordSignUpReq(
        @NotEmpty(message = "id는 필수 입력입니다.")
        String id,
        @NotEmpty(message = "이름은 필수입니다.")
        String name,
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,
        @NotEmpty(message = "비밀번호는 필수입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.")
        String password
){
}
