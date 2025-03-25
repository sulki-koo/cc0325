package cookcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cookcloud.entity.Allergy;
import cookcloud.service.AllergyService;

@Controller
@RequestMapping("/allergy")
public class AllergyController {

	@Autowired
	private AllergyService allergyService;
	
	
}
