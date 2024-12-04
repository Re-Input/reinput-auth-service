package info.reinput.member.infra;

import info.reinput.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomMemberRepository {
    Optional<Member> findBySocialInfoSocialId(String socialId);
}
