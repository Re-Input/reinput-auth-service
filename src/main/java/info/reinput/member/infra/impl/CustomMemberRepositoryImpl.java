package info.reinput.member.infra.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.reinput.member.domain.Member;
import info.reinput.member.infra.CustomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static info.reinput.member.domain.QSocialMember.socialMember;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findBySocialInfoSocialId(String socialId) {
        if (socialId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(queryFactory
                .selectFrom(socialMember)
                .where(socialIdEq(socialId))
                .fetchOne());
    }

    @Override
    public boolean existBySocialId(String socialId) {
        if (socialId == null) {
            return false;
        }

        Integer fetchOne = queryFactory
                .selectOne()
                .from(socialMember)
                .where(socialIdEq(socialId))
                .fetchFirst();

        return fetchOne != null;
    }

    private BooleanExpression socialIdEq(String socialId) {
        return socialMember.socialInfo.socialId.eq(socialId);
    }
}