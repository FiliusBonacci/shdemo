package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Grade;
import com.example.shdemo.domain.Student;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class TeacherHibernateImpl implements Teacher {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

//----------------------------------------------------------------------------------------------
	@Override
	public void addStudent(Student student) {
		student.setId(null);
		sessionFactory.getCurrentSession().persist(student);
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<Student> getAllStudents() {
        return sessionFactory.getCurrentSession().getNamedQuery("student.all").list();
	}

	@Override
	public void deleteStudent(Student student) {
        student = (Student) sessionFactory.getCurrentSession().get(Student.class, student.getId());

        // lazy loading here
        for (Grade grade : student.getGrades()) {
            grade.setApproved(false);
            sessionFactory.getCurrentSession().update(grade);
        }
        sessionFactory.getCurrentSession().delete(student);
	}

	@Override
	public Student findStudentById(Long id) {
		return (Student) sessionFactory.getCurrentSession().getNamedQuery("student.byId").setString("id", id).uniqueResult();
	}

	@Override
	public Long addNewGrade(Grade grade) {
		grade.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(grade);
	}

	@Override
	public List<Grade> getAvailableGrades() {
        return sessionFactory.getCurrentSession().getNamedQuery("grade.avaliable").list();
    }

	@Override
	public void putGrade(Student student, Long grade) {
        student = (Student) sessionFactory.getCurrentSession().get(Student.class,
                student.getId());
        grade = (Grade) sessionFactory.getCurrentSession().get(Grade.class,
                grade.getId());

        Grade toRemove = null;
        // lazy loading here (student.getGrades)
        for (Grade sGrade : student.getGrades())
            if (sGrade.getId().compareTo(grade.getId()) == 0) {
                toRemove = sGrade;
                break;
            }

        if (toRemove != null)
            student.getGrades().remove(toRemove);

        grade.setApproved(false);
	}

	@Override
	public List<Grade> getStudentgrades(Student student) {
		return student.getGrades();
	}
}
