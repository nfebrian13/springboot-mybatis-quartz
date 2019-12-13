package com.mybatis.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.app.model.Student;
import com.mybatis.app.dao.StudentDAO;

@Service
public class StudentService {

	@Autowired
	private StudentDAO studentDao;

	public List<Student> findAll() {
		return studentDao.findAll();
	}
}
