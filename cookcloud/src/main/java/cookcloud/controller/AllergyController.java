package cookcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cookcloud.service.AllergyService;

@Controller
@RequestMapping("/allergy")
public class AllergyController {

	@Autowired
	private AllergyService allergyService;
	
	
}
