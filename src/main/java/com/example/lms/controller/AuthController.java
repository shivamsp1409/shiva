package com.example.lms.controller;

import com.example.lms.model.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthController {
	@GetMapping("/")
	public String home(HttpSession session) {
		Student student = (Student) session.getAttribute("student");
		return (student != null) ? "redirect:/dashboard" : "redirect:/login";
	}

	@GetMapping("/login")
	public String loginPage(HttpSession session, Model model) {
		if (session.getAttribute("student") != null) {
			return "redirect:/dashboard";
		}
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(@RequestParam("fullName") String fullName,
			@RequestParam("email") String email,
			HttpSession session,
			Model model) {
		if (!StringUtils.hasText(fullName) || !StringUtils.hasText(email)) {
			model.addAttribute("error", "Please enter your name and email");
			return "login";
		}
		Student student = new Student(UUID.randomUUID().toString(), fullName.trim(), email.trim(), null);
		session.setAttribute("student", student);
		return "redirect:/dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login?logout";
	}
}