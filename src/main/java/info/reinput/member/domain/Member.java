package info.reinput.member.domain;

import info.reinput.folder.domain.Folder;
import info.reinput.global.domain.TimeAudit;
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
    private MemberSocial social;

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
    public Member(MemberSocial social, MemberInfo info, MemberRole role) {
        this.social = social;
        this.info = info;
        this.role = role;
    }

    public static Member create(MemberSocial social, MemberInfo info, MemberRole role) {
        return Member.builder()
                .social(social)
                .info(info)
                .role(role)
                .build();
    }
}
