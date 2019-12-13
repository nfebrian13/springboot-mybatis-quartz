package com.mybatis.app.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mybatis.app.model.Student;
import com.mybatis.app.service.StudentService;

@Controller
public class StudentMvcController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/home")
	public String listJob(Model model) {
		List<Student> studentList = studentService.findAll();
		model.addAttribute("studentlist", studentList);
		return "home/index";
	}
}
