package cookcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Member;
import cookcloud.entity.MemberAllergyFood;
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
    
    @GetMapping("/checkMemId")
    @ResponseBody
    public Map<String, Object> checkMemId(@RequestParam String memId){
    	Map<String, Object> response = new HashMap<>();
    	
    	if(memId != null && !memId.isEmpty()) {
    		memId = memId.trim();
    		boolean idExists = memberService.checkIdExists(memId);
    		response.put("exists", idExists);
    	} 
    	return response;
    }
    
    @GetMapping("/checkMemNickname")
    @ResponseBody
    public Map<String, Object> checkMemNickname(@RequestParam String memNickname){
    	Map<String, Object> response = new HashMap<>();
    	
    	if(memNickname != null && !memNickname.isEmpty()) {
    		boolean nicknameExists = memberService.checkNicknameExists(memNickname);
    		response.put("exists", nicknameExists);
    	}
    	return response;
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String registerMember(@ModelAttribute @Valid Member member, Model model) {
        try {
            memberService.registerMember(member.getMemId(), member.getMemPassword(), member.getMemName(), member.getMemNickname(), member.getMemEmail(), member.getMemPhone());
           
            // 알러지 정보 저장
            if (member.getMemberAllergyFoodList() != null && !member.getMemberAllergyFoodList().isEmpty()) {
                for (MemberAllergyFood allergyFood : member.getMemberAllergyFoodList()) {
                    allergyFood.setMemId(member.getMemId());  // MEM_ID 설정
                    memberAllergyFoodService.insertMemAllergyFood(allergyFood); // 서비스 호출
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
