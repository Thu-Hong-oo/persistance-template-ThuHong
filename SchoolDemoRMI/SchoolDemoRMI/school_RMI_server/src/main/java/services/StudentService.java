package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.StudentDAO;
import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class StudentService extends UnicastRemoteObject implements StudentDAO {

	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public StudentService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean add(Student student) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(student);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public List<Student> findByEnrollmentInYear(int year) throws RemoteException {
		return entityManager.createNamedQuery("Student.findByEnrollmentInYear", Student.class)
				.setParameter("year", year).getResultList();
	}

	@Override
	public List<Student> findStudentsEnrolledInCourseByCourseTitle(String title) throws RemoteException {
		return entityManager.createNamedQuery("Student.findStudentsEnrolledInCourseByCourseTitle", Student.class)
				.setParameter("title", "%" + title + "%").getResultList();
	}

	@Override
	public Map<Student, Double> findStudentGPAs(int year) throws RemoteException {

		Map<Student, Double> map = new LinkedHashMap<>();

		String query = "SELECT s.id, AVG(sg.grade) as avg " + "FROM Student s INNER JOIN s.studentGrades sg "
				+ "WHERE year(enrollmentDate) = :year " + "GROUP BY s.id " + "ORDER BY avg DESC";
		List<?> list = entityManager.createQuery(query).setParameter("year", year).getResultList();

		list.stream().map(obj -> (Object[]) obj).forEach(a -> {
			int studentID = (int) a[0];
			double avg = (double) a[1];

			Student student = entityManager.find(Student.class, studentID);

			map.put(student, avg);
		});

		return map;
	}

	@Override
	public Map<Student, Double> findStudentMaxGPAs(int year) throws RemoteException {

		Map<Student, Double> map = new HashMap<>();

		String query = "SELECT s.id, AVG(sg.grade) as avg " + "FROM Student s INNER JOIN s.studentGrades sg "
				+ "WHERE year(enrollmentDate) =: year " + "GROUP BY s.id " + "Having AVG(sg.grade) >= ALL("
				+ "SELECT AVG(sg.grade) " + "FROM Student s INNER JOIN s.studentGrades sg "
				+ "WHERE year(enrollmentDate) =: year " + "GROUP BY s.id )";

		List<?> list = entityManager.createQuery(query).setParameter("year", year).getResultList();

		list.stream().map(obj -> (Object[]) obj).forEach(a -> {
			int studentID = (int) a[0];
			double avg = (double) a[1];
			Student student = entityManager.find(Student.class, studentID);

			map.put(student, avg);
		});

		return map;
	}

	@Override
	public List<Student> findAll() throws RemoteException {
		return entityManager.createNamedQuery("Student.findAll", Student.class).getResultList();
	}

}
