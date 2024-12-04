package info.reinput.member.infra.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import info.reinput.member.domain.Member;
import info.reinput.member.infra.CustomMemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static info.reinput.member.domain.QSocialMember.socialMember;


@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Member> findBySocialInfoSocialId(String socialId){
        return Optional.ofNullable(queryFactory
                .selectFrom(socialMember)
                .where(socialMember.socialInfo.socialId.eq(socialId))
                .fetchOne());
    }
}
