package cookcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인 페이지
    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    // 냉장고 털기 페이지
    @GetMapping("/fridge")
    public String fridgePage() {
        return "fridge"; // fridge.html
    }

    // 취향 선택 추천 페이지
    @GetMapping("/recommendation")
    public String recommendationPage() {
        return "recommendation"; // recommendation.html
    }

    // 공지 & 문의 페이지
    @GetMapping("/notice")
    public String noticePage() {
        return "notice"; // notice.html
    }
    // 레시피 게시판 페이지
    @GetMapping("/recipes")
    public String recipeBoard() {
        return "recipes"; // recipes.html 페이지로 이동
    }
}
