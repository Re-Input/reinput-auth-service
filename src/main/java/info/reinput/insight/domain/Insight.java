package info.reinput.insight.domain;

import info.reinput.folder.domain.Folder;
import info.reinput.global.domain.Images;
import info.reinput.global.domain.TimeAudit;
import info.reinput.reminder.domain.Reminder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "insight")
@Entity
public class Insight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insight_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    private InsightSummary summary;

    @ElementCollection
    @CollectionTable(name = "insight_images", joinColumns = @JoinColumn(name = "insight_id"))
    private List<Images> images;

    @Embedded
    private InsightDetail detail;

    @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToOne(mappedBy = "insight", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reminder reminder;

    @Embedded
    private TimeAudit timeAudit;

}
