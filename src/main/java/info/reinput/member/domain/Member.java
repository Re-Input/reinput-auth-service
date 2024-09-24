package info.reinput.member.domain;

import info.reinput.folder.domain.Folder;
import info.reinput.global.domain.TimeAudit;
import jakarta.persistence.*;
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

}
