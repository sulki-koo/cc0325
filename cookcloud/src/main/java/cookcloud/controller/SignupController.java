package cookcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Member;
import cookcloud.service.AllergyService;
import cookcloud.service.MemberAllergyFoodService;
import cookcloud.service.MemberService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private AllergyService allergyService;

	@Autowired
	private MemberAllergyFoodService memberAllergyFoodService;

	// 회원가입 페이지
	@GetMapping
	public String showSignupPage(Model model) {
		// model.addAttribute("allergyList", allergyService.getAllAllergies());
		model.addAttribute("member", new Member());
		return "signup"; // signup.html 페이지 반환
	}

	@PostMapping("/checkDuplicate")
	@ResponseBody
	public Map<String, Boolean> checkDuplicate(@RequestBody Map<String, String> request) {
		Map<String, Boolean> resultMap = new HashMap<>();
		String memId = request.get("memId");
		String memNickname = request.get("memNickname");
		System.out.println("입력한 닉네임======" + memNickname);
		
		boolean result = memberService.isDuplicate(memId, memNickname); 
		
		System.out.println("결과값======" + result);
		
		resultMap.put("result", result);
		return resultMap;
	}

	// 회원가입 처리
	@PostMapping("/insertMember")
	@ResponseBody
	public void insertMember(@RequestBody Member member, Model model) {

		member.setMemId(member.getMemId());
		member.setMemPassword(member.getMemPassword());
		member.setMemName(member.getMemName());
		member.setMemNickname(member.getMemNickname());
		member.setMemEmail(member.getMemEmail());
		member.setMemPhone(member.getMemPhone());
		memberService.insertMember(member);
		// 알러지 정보 저장
//            if (member.getMemberAllergyFoodList() != null && !member.getMemberAllergyFoodList().isEmpty()) {
//                for (MemberAllergyFood allergyFood : member.getMemberAllergyFoodList()) {
//                    MemberAllergyFood newMemberAllergyFood = new MemberAllergyFood();
//                    newMemberAllergyFood.setMemId(member.getMemId());
//                    newMemberAllergyFood.setAllergyId(allergyFood.getAllergyId());
//                    newMemberAllergyFood.setMemAllergyInsertAt(LocalDateTime.now());
//                    newMemberAllergyFood.setMemAllergyIsDeleted("N");  // 초기값 설정
//                    memberAllergyFoodService.insertMemAllergyFood(newMemberAllergyFood);
//                }
//            }

	}

}
