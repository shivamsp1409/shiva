package com.example.lms.controller;

import com.example.lms.model.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class DashboardController {
	@Value("${app.upload-dir:${user.dir}/uploads}")
	private String uploadDir;

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		Student student = (Student) session.getAttribute("student");
		if (student == null) {
			return "redirect:/login";
		}
		model.addAttribute("student", student);
		return "dashboard";
	}

	@PostMapping("/profile/avatar")
	public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar,
			HttpSession session,
			Model model) throws IOException {
		Student student = (Student) session.getAttribute("student");
		if (student == null) {
			return "redirect:/login";
		}
		if (avatar == null || avatar.isEmpty()) {
			return "redirect:/dashboard?error=emptyAvatar";
		}

		String originalFilename = Objects.requireNonNull(avatar.getOriginalFilename(), "avatar filename");
		String extension = "";
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex >= 0) {
			extension = originalFilename.substring(dotIndex);
		}
		String newFilename = UUID.randomUUID() + extension;
		Path avatarDir = Paths.get(uploadDir, "avatars").toAbsolutePath().normalize();
		Files.createDirectories(avatarDir);
		Path destination = avatarDir.resolve(newFilename);
		Files.copy(avatar.getInputStream(), destination);

		String publicUrl = "/uploads/avatars/" + newFilename;
		student.setAvatarUrl(publicUrl);
		session.setAttribute("student", student);
		return "redirect:/dashboard?updated=avatar";
	}
}