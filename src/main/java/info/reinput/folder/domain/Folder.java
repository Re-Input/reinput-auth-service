package info.reinput.folder.domain;

import info.reinput.global.domain.Color;
import info.reinput.global.domain.Share;
import info.reinput.global.domain.TimeAudit;
import info.reinput.insight.domain.Insight;
import info.reinput.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "folder")
@SecondaryTable(name = "folder_share", pkJoinColumns = @PrimaryKeyJoinColumn(name = "folder_id"))
public class Folder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long id;

    @Column(name = "folder_name")
    private String name;

    @Column(name = "folder_color")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "share_id")),
            @AttributeOverride(name = "isCopyable", column = @Column(name = "is_copyable")),
            @AttributeOverride(name = "shareUrl", column = @Column(name = "share_url"))
    })
    private Share share;

    //insights
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Insight> insights;

    @Embedded
    private TimeAudit timeAudit;
}
