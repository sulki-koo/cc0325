package cookcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cookcloud.entity.Member;
import cookcloud.service.AllergyService;
import cookcloud.service.MemberAllergyFoodService;
import cookcloud.service.MemberService;
import jakarta.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private AllergyService allergyService;
    
    @Autowired
    private MemberAllergyFoodService memberAllergyFoodService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
    	
    	model.addAttribute("allergyList", allergyService.getAllAllergies());
    	model.addAttribute("member", new Member());
        return "signup";  // signup.html 페이지 반환
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody Member member) {
        // 아이디 중복 체크
        if (memberService.isMemberIdExists(member.getMemId())) {
            return ResponseEntity.badRequest().body("아이디가 이미 존재합니다.");
        }
        
        // 닉네임 중복 체크
        if (memberService.isNicknameExists(member.getMemNickname())) {
            return ResponseEntity.badRequest().body("닉네임이 이미 존재합니다.");
        }

        // 회원가입 처리
        memberService.registerMember(member);
        return ResponseEntity.ok("회원가입 성공");
    }
    
}
