package com.cst8333.admin.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cst8333.admin.user.UserNotFoundException;
import com.cst8333.admin.user.UserService;
import com.cst8333.common.entity.Role;
import com.cst8333.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listFirstPage(Model model) {
//		List<User> listUsers = service.listAll();
//		model.addAttribute("listUsers", listUsers);
//		return "users";
		return listByPage(1, model, "firstName", "asc", null);	
		}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword){
		
		System.out.println("Sort Field: " +sortField);
		System.out.println("Sort Order: " +sortDir);
		
		Page <User> page= service.listByPage(pageNum, sortField, sortDir, keyword);
		List <User> listUsers = page.getContent();
		
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE + 1;
		if (endCount > page.getTotalElements()) {
			endCount =  page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);

		
		
		return "users/users";
		}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user = User.builder().build();
		user.setEnabled(true);
		List<Role> listRoles = service.listAllRoles();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "users/userform";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectattributes) {
		System.out.println(user);
		service.save(user);
		redirectattributes.addFlashAttribute("message","The user has been saved succesfully");
		
		return getRedirectUrlToAffectedUser(user);
	}

	private String getRedirectUrlToAffectedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+ firstPartOfEmail;
	}
	
	@GetMapping("/users/edit/{userid}")
	public  String editUser(@PathVariable(name = "userid") Integer id, Model model, RedirectAttributes redirectattributes) {
		try {
			User user = service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User (ID : "+id+")");
			List<Role> listRoles = service.listAllRoles();
			model.addAttribute("listRoles", listRoles);
			return "users/userform";
		} catch (UserNotFoundException e) {
			redirectattributes.addFlashAttribute("message", e.getMessage());
		}
			return "redirect:/users";
	}
	
	@GetMapping("/users/delete/{userid}")
	public  String deleteUser(@PathVariable(name = "userid") Integer id, Model model, RedirectAttributes redirectattributes) {
		try {
			service.delete(id);
			redirectattributes.addFlashAttribute("message","The user ID: " + id + " been deleted succesfully");
		} catch (UserNotFoundException e) {
			redirectattributes.addFlashAttribute("message",e.getMessage());
		}
			return "redirect:/users";
	}
	
	@GetMapping("/users/{userid}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable(name="userid") Integer id, @PathVariable(name="status") boolean enabled,
			RedirectAttributes redirectattributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled": "disabled";
		String message = "The user ID " + id + " has been " +status;
		redirectattributes.addFlashAttribute("message",message);
		
		return "redirect:/users";
	}
	
	
}
