package info.reinput.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(nullable = false, name = "member_birth")
    private LocalDate birth;

    @Column(name = "member_profile_image")
    private String profileImage;

    @Column(name = "member_job")
    @Enumerated(EnumType.STRING)
    private Job job;
}
