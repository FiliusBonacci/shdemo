package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.shdemo.domain.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Car;
import com.example.shdemo.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class TeacherTest {

	@Autowired
	Teacher teacher;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

	private final String NAME1 = "Student1";
    private final long ID1 = 100;
	String date1 = "January 2, 1980";
	private final LocalDate BIRTHDAY1 = LocalDate.parse(date1, formatter);
    private final Date BIRTHDAY_1 = new Date(1980, 1,14);

	private final Long ID2 = Long.valueOf(200);
	private final String NAME_2 = "Student2";
    String date2 = "February 2, 1980";
	private final LocalDate BIRTHDAY2 = LocalDate.parse(date2, formatter);
	private final Date BIRTHDAY_2 = new Date(1988,2,6);

	private final float GRADE1 = (float) 4.5;
	private final boolean APPROVED1 = true;

    private final float GRADE2 = (float) 3.5;
    private final boolean APPROVED2 = true;


	@Test
	public void addClientCheck() {

		List<Student> retrievedStudents = teacher.getAllStudents();

		// If there is a student with PIN_1 delete it
		for (Student student : retrievedStudents) {
			if (student.getId().equals(ID1)) {
				teacher.deleteStudent(student);
			}
		}

		Student student = new Student();
		student.setFirstName(NAME1);
		student.setId(ID1);
		student.setDateOfBirth(BIRTHDAY_1);

		// id is Unique
		teacher.addStudent(student);

		Student retrievedStudent = teacher.findStudentById(ID1);

		assertEquals(NAME1, retrievedStudent.getFirstName());
		assertEquals(BIRTHDAY1, retrievedStudent.getDateOfBirth());
		
	}

	@Test
	public void addGradeCheck() {

		Grade grade = new Grade();
		grade.setValue(GRADE1);
		grade.setApproved(APPROVED1);


//		Long gradeId = teacher.addNewGrade(grade);

		Grade retrievedGrade = teacher.findGradeByValue(GRADE1);
		assertEquals(GRADE1, retrievedGrade.getValue());
		assertEquals(APPROVED1, retrievedGrade.getApproved());

	}

	@Test
	public void putGradeCheck() {

		Student student = new Student();
		student.setFirstName(NAME_2);
		student.setDateOfBirth(BIRTHDAY_2);

		teacher.addStudent(student);

		Student retrievedStudent = teacher.findStudentById(ID2);

		Grade grade = new Grade();
		grade.setValue(GRADE2);
		grade.setApproved(APPROVED2);

		Long gradeId = teacher.addNewGrade(grade);

		teacher.putGrade(retrievedStudent, gradeId);

		List<Grade> studentsGrades = teacher.getStudentgrades(retrievedStudent);

		assertEquals(1, studentsGrades.size());
		assertEquals(GRADE2, studentsGrades.get(0).getValue());
		assertEquals(APPROVED2, studentsGrades.get(0).getApproved());
	}

}
