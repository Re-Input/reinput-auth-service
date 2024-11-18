package info.reinput.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberInfo {
    @Column(nullable = false, name = "member_name")
    private String name;

    @Column(name = "member_nickname")
    private String nickname;

    @Column(name = "member_profile_image")
    private String profileImage;

    @Column(name = "member_enable")
    private boolean enable;

    @Column(name = "member_job")
    @Enumerated(EnumType.STRING)
    private Job job;

    @ElementCollection
    @CollectionTable(name = "member_topics", joinColumns = @JoinColumn(name = "member_id"))
    List<String> topics = new ArrayList<>();

    @Column(name = "member_isOnboarded")
    private boolean isOnboarded;

    public void disable() {
        this.enable = false;
    }

    public void enable() {
        this.enable = true;
    }
}
