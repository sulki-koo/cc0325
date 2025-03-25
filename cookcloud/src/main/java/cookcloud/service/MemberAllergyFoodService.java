package cookcloud.service;

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
		memberAllergyFoodRepository.save(memberAllergyFood);
	}
	
	
}
