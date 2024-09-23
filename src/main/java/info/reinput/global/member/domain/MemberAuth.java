package info.reinput.global.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAuth {
    @Column(nullable = false, name = "member_id")
    private String password;

    @Column(nullable = false, name = "member_is_enable")
    private boolean isEnable;

    @Column(name = "member_last_login_at")
    private LocalDateTime lastLoginAt;
}
