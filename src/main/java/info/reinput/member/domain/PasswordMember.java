package info.reinput.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PASSWORD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordMember extends Member {

    @Column(name = "member_password")
    private String password;

    @Builder
    private PasswordMember(MemberInfo memberInfo, String password, MemberRole role) {
        super(memberInfo, role);
        this.password = password;
    }

}
