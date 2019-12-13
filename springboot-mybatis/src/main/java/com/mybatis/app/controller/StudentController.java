package com.mybatis.app.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.app.dto.ApiResult;
import com.mybatis.app.model.Student;
import com.mybatis.app.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	private static Logger logger = LogManager.getLogger();

	@Autowired
	private StudentService studentService;

	@RequestMapping("/list")
	public ApiResult getListStudents() {
		logger.debug("This is a debug message");
		ApiResult response = new ApiResult();
		List<Student> studentList = studentService.findAll();
		response.setData(studentList);
		return response;
	}

}
