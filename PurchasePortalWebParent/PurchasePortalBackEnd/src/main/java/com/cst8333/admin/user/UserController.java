package com.cst8333.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cst8333.common.entity.Role;
import com.cst8333.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = User.builder().build();
		user.setEnabled(true);
		List<Role> listRoles = service.listAllRoles();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "userform";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectattributes) {
		System.out.println(user);
		service.save(user);
		redirectattributes.addFlashAttribute("message","The user has been saved succesfully");
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{userid}")
	public  String editUser(@PathVariable(name = "userid") Integer id, Model model, RedirectAttributes redirectattributes) {
		try {
			User user = service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User (ID : "+id+")");
			List<Role> listRoles = service.listAllRoles();
			model.addAttribute("listRoles", listRoles);
			return "userform";
		} catch (UserNotFoundException e) {
			redirectattributes.addFlashAttribute("message",e.getMessage());
		}
			return "redirect:/users";
	}
	
}
