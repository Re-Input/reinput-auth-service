package info.reinput.member.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SOCIAL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialMember extends Member{

    @Embedded
    private SocialInfo socialInfo;

    @Builder
    private SocialMember(MemberInfo memberInfo, SocialInfo socialInfo, MemberRole role){
        super(memberInfo, role);
        this.socialInfo = socialInfo;
    }

    public static SocialMember create(MemberInfo memberInfo, SocialInfo socialInfo, MemberRole role){
        return SocialMember.builder()
                .memberInfo(memberInfo)
                .socialInfo(socialInfo)
                .role(role)
                .build();
    }
}
