package services;

import java.util.List;

import dao.StudentDAO;
import entities.Student;
import jakarta.persistence.EntityManager;

public class StudentService implements StudentDAO{
	
	private EntityManager entityManager;
	
	public StudentService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Student> findByEnrollmentInYear(int year) {
		return entityManager.createNamedQuery("Student.findByEnrollmentInYear", Student.class)
				.setParameter("year", year)
				.getResultList();
	}

	@Override
	public List<Student> findStudentsEnrolledInCourseByCourseTitle(String title) {
		return entityManager.createNamedQuery("Student.findStudentsEnrolledInCourseByCourseTitle", Student.class)
				.setParameter("title", "%" + title + "%")
				.getResultList();
	}


}
