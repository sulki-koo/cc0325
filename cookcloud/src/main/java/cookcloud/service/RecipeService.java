package cookcloud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.repository.MemberRepository;
import cookcloud.repository.RecipeRepository;
import cookcloud.service.RecipeService;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private MemberRepository memberRepository;

	public List<Recipe> getMemberRecipes(String memNickname) {
		try {
			Member member = memberRepository.findAll().stream()
					.filter(m -> m.getMemNickname().equals(memNickname)).findFirst()
					.orElseThrow(() -> new IllegalAccessException("닉네임 " + memNickname + " 확인불가"));

			 return recipeRepository.findAll().stream()
		                .filter(recipe -> recipe.getMember().getMemId().equals(member.getMemId()))  // id 비교
		                .collect(Collectors.toList());
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return null;
	}

	// 개인 레시피 목록 조회
    public List<Recipe> getMyRecipes(String memId) {
    	List<Recipe> recipes = recipeRepository.findByMemberMemId(memId);
        
        // 각 레시피에 첫 번째 첨부파일 URL을 설정
        for (Recipe recipe : recipes) {
            if (!recipe.getAttachList().isEmpty()) {
                recipe.setImageUrl(recipe.getAttachList().get(0).getAttachServerName());
            }
        }
        return recipes;
    }
	
}
