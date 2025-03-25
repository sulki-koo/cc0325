package cookcloud.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cookcloud.entity.Member;
import cookcloud.entity.MemberAllergyFood;
import cookcloud.service.AllergyService;
import cookcloud.service.MemberAllergyFoodService;
import cookcloud.service.MemberService;
import jakarta.validation.Valid;

@Controller
public class SignupController {

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

    // 회원가입 처리
    @PostMapping("/signup")
    public String registerMember(@ModelAttribute @Valid Member member, Model model) {
    	
        try {
            memberService.registerMember(member.getMemId(), member.getMemPassword(), member.getMemName(), member.getMemNickname(), member.getMemEmail(), member.getMemPhone());
            // 알러지 정보 저장
            if (member.getMemberAllergyFoodList() != null && !member.getMemberAllergyFoodList().isEmpty()) {
                for (MemberAllergyFood allergyFood : member.getMemberAllergyFoodList()) {
                    MemberAllergyFood newMemberAllergyFood = new MemberAllergyFood();
                    newMemberAllergyFood.setMemId(member.getMemId());
                    newMemberAllergyFood.setAllergyId(allergyFood.getAllergyId());
                    newMemberAllergyFood.setMemAllergyInsertAt(LocalDateTime.now());
                    newMemberAllergyFood.setMemAllergyIsDeleted("N");  // 초기값 설정
                    memberAllergyFoodService.insertMemAllergyFood(newMemberAllergyFood);
                }
            }
            
            model.addAttribute("message", "회원가입 성공! 로그인하세요.");
            return "login";  // 회원가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 실패: " + e.getMessage());
            return "signup";  // 실패 시 다시 회원가입 페이지로 이동
        }
    }
}
