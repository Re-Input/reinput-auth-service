package info.reinput.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEmail {

    @Column(nullable = false, name = "member_email")
    private String email;

    @Column(name = "member_recover_email")
    private String recoverEmail;
}
