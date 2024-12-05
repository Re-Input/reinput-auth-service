package info.reinput.member.infra;

import info.reinput.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRespository extends JpaRepository<Member, Long>, CustomMemberRepository {

}
