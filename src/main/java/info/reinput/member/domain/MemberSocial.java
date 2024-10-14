package info.reinput.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSocial {

    @Column(nullable = false, name = "member_social_id")
    private String socialId;

    @Column(nullable = false, name = "member_social")
    private SocialType socialType;


}
