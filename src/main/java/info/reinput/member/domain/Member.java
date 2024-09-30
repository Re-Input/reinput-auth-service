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

    public static Member signUp(SignUpReq signUpReq) {
        return Member.builder()
                .email(MemberEmail.builder()
                        .email(signUpReq.email())
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


}
