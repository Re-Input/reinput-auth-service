package info.reinput.reminder.domain;


import info.reinput.insight.domain.Insight;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reminder")
@Entity
public class Reminder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminder_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insight_id")
    private Insight insight;

    @Column(name = "is_enable")
    private Boolean isEnable;



}
