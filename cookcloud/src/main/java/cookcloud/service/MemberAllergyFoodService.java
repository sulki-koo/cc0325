package cookcloud.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.MemberAllergyFood;
import cookcloud.repository.MemberAllergyFoodRepository;
import cookcloud.service.MemberAllergyFoodService;

@Service
public class MemberAllergyFoodService {

	@Autowired
	private MemberAllergyFoodRepository memberAllergyFoodRepository;
	
	public void insertMemAllergyFood(MemberAllergyFood memberAllergyFood) {
		memberAllergyFood.setMemAllergyInsertAt(LocalDateTime.now()); // 삽입 시간 설정
        memberAllergyFood.setMemAllergyIsDeleted("N"); // 삭제되지 않은 상태로 설정
        memberAllergyFoodRepository.save(memberAllergyFood);
	}
	
	
}
