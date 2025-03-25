package cookcloud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cookcloud.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{

	Optional<Member> findByMemNickname(String memNickname);
	
	boolean existsByMemId(String memId); // 아이디 중복 체크
    boolean existsByMemNickname(String memNickname); // 닉네임 중복 체크
	
}