package info.reinput.member.domain;

import info.reinput.folder.domain.Folder;
import info.reinput.global.domain.TimeAudit;
import info.reinput.member.domain.dto.req.SignUpReq;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "member")
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private MemberEmail email;

    @Embedded
    private MemberAuth auth;

    @Embedded
    private MemberInfo info;

    @Getter
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders;

    @Embedded
    private TimeAudit timeAudit;

    @Builder
    public Member(MemberEmail email, MemberAuth auth, MemberInfo info, MemberRole role) {
        this.email = email;
        this.auth = auth;
        this.info = info;
        this.role = role;
    }

    public static boolean validateEmailCode(String authCode, String storedAuthCode) {
        return storedAuthCode.equals(authCode);
    }

    public static Member signUp(SignUpReq signUpReq, String storedAuthCode) {
        boolean emailVerified = validateEmailCode(signUpReq.mailCode(), storedAuthCode);
        if (!emailVerified) {
            throw new IllegalArgumentException("인증 코드가 일치하지 않습니다.");
        }
        return Member.builder()
                .email(MemberEmail.builder()
                        .email(signUpReq.email())
                        .emailVerified(true)
                        .build())
                .auth(MemberAuth.builder()
                        .password(signUpReq.password())
                        .build())
                .info(MemberInfo.builder()
                        .name(signUpReq.name())
                        .birth(signUpReq.birth())
                        .job(signUpReq.job())
                        .enable(true)
                        .build())
                .role(MemberRole.USER)
                .build();
    }

    public String getPassword() {
        return auth.getPassword();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public boolean getVerified() {
        return email.getEmailVerified();
    }
}
