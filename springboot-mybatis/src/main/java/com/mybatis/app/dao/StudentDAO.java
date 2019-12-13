package com.mybatis.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mybatis.app.mapper.StudentMapper;
import com.mybatis.app.model.Student;

@Component
public class StudentDAO {

	@Autowired
	private StudentMapper studentMapper;

	public List<Student> findAll() {
		return studentMapper.findAll();
	}
}
