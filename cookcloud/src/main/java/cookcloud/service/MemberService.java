package cookcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cookcloud.entity.Member;
import cookcloud.entity.MemberAllergyFood;
import cookcloud.repository.MemberRepository;
import cookcloud.service.MemberService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public Member findByMemNickname(String memNickname) {
		Member member = memberRepository.findByMemNickname(memNickname)
				.orElseThrow(() -> new RuntimeException("회원 확인 불가"));
		return member;
	}

	// 아이디 중복 체크
	public boolean isMemberIdExists(String memId) {
		return memberRepository.existsByMemId(memId);
	}

	// 닉네임 중복 체크
	public boolean isNicknameExists(String memNickname) {
		return memberRepository.existsByMemNickname(memNickname);
	}

	// 회원가입 처리
	public void registerMember(Member member) {
		memberRepository.save(member);
	}

}
