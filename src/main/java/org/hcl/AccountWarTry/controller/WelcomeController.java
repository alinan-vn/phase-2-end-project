package org.hcl.AccountWarTry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String Welcome(@RequestParam(value = "name", defaultValue = "WoRlD", required = true) String name, Model model) {
		model.addAttribute("name", name);
		
		return "welcome";
		
	}
}
