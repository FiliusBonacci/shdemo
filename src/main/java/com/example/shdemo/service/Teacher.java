package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Grade;
import com.example.shdemo.domain.Student;

public interface Teacher {
	
	void addStudent(Student student);
	List<Student> getAllStudents();
	void deleteStudent(Student student);
	Student findStudentById(Long id);
	
	Long addNewGrade(Grade grade);
    Grade findGradeByValue(float value);
	List<Grade> getAvailableGrades();
	void putGrade(Student student, Long grade);

	List<Grade> getStudentgrades(Student student);

}
