package org.hcl.AccountWarTry.controller;

import java.util.ArrayList;
import java.util.List;

import org.hcl.AccountWarTry.crudRepository.UserEntityCrudRepository;
import org.hcl.AccountWarTry.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AccountController {
	
	@Autowired
	UserEntityCrudRepository userEntityCrudRepository;

	@GetMapping(path = "/createAccount")
	public String CreateAccountGet(Model model) {
		
		UserEntity newUser = new UserEntity();
		model.addAttribute("newUser", newUser);
		return "createAccount";
	}
	
	@PostMapping(path = "/createAccount")
	public void CreateAccountPost(@ModelAttribute("userFormData") UserEntity userEntity, BindingResult result) {

		System.out.println("==== ACCOUNTCONTROLLER - CreateAccount");
		System.out.println("==== Form Data: ");
		System.out.println("==== Username: " + userEntity.getName());
		System.out.println("==== Password: " + userEntity.getPassword());
						
		if (userEntity == null || userEntity.getName() == null) {
			throw new RuntimeException("Name Required");
		} 
		if (userEntity.getPassword() == null) {
			throw new RuntimeException("Password Required");
		}
		userEntityCrudRepository.save(userEntity);
	}
	
	@GetMapping(path = "/login")
	public String LoginGet(Model model) {
	
		UserEntity userData = new UserEntity();
		model.addAttribute("userData", userData);	
		return "login";
	}
	
	@PostMapping(path = "/login")
	public void LoginPost(@ModelAttribute("userFormData") UserEntity userData, BindingResult result) {
		
		System.out.println("ACCOUNTCONTROLLER - Login");
		System.out.println("Form Data: ");
		System.out.println("Username: " + userData.getName());
		System.out.println("Password: " + userData.getPassword());
		
		if (userData == null || userData.getName() == null) {
			throw new RuntimeException("Name Required");
		} 
		if (userData.getPassword() == null) {
			throw new RuntimeException("Password Required");
		}
		
		Boolean userLoginCorrect = false;
		
		Iterable<UserEntity> users = userEntityCrudRepository.findAll();
		for(UserEntity u : users) {
			System.out.println("CHECKING USERNAMES: " + u.getName() + " vs " + userData.getName());
			int nameRes = u.getName().compareTo(userData.getName());
			if (nameRes == 0) {
				System.out.println("CHECKING PASSWORDS: " + u.getPassword() + " vs " + userData.getPassword());
				int passwordRes = u.getName().compareTo(userData.getName());
				if (passwordRes == 0) {
					userLoginCorrect = true;
				}
			}
		}
		
		if (userLoginCorrect) {
			System.out.println("++++USER LOGIN SUCCESSFUL++++");
		} else {
			System.out.println("----USER LOGIN FALSE----");
		}
	}
	
	@GetMapping(path = "/allAccounts")
	public String ShowAccounts(@RequestParam(value = "userHtml", defaultValue = "null", required = true) String userHtml, Model model) {
		Iterable<UserEntity> users = userEntityCrudRepository.findAll();
		String allUserCodeHtml = "<ul>";
		
		for( UserEntity u : users) {
			allUserCodeHtml += "<li>";
			allUserCodeHtml += u.getId();
			allUserCodeHtml += " ==== ";
			allUserCodeHtml += u.getName();
			allUserCodeHtml += " === ";
			allUserCodeHtml += u.getPassword();
			allUserCodeHtml += "</li>";			
		}
		allUserCodeHtml += "</ul>";
		model.addAttribute("userHtml", allUserCodeHtml);

		return "allAccounts";
	}
}









