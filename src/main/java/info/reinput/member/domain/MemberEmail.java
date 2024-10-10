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

    @Column(name = "member_email_verified")
    private boolean emailVerified;

    @Column(name = "member_recover_email")
    private String recoverEmail;

    public void verifyEmail() {
        this.emailVerified = true;
    }

    public boolean getEmailVerified() {
        return this.emailVerified;
    }
}
