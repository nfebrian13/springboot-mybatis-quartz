package com.mybatis.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mybatis.app.model.Student;

@Mapper
public interface StudentMapper {

	/**
	 * find all the students.
	 */
	List<Student> findAll();
}
