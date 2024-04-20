package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dao.CourseDAO;
import entities.Course;
import entities.OnlineCourse;
import entities.OnsiteCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CourseService extends UnicastRemoteObject implements CourseDAO {

	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public CourseService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean addCourse(Course course) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.persist(course);
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
	public boolean updateCourse(Course course) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(course);
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
	public boolean deleteCourse(int id) throws RemoteException {
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			Course course = entityManager.find(Course.class, id);
			entityManager.remove(course);
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
	public Course findCourseByID(int id) throws RemoteException {
		return entityManager.find(Course.class, id);
	}

	@Override
	public Course findCourseByTitle(String title) throws RemoteException {
//		return entityManager.createQuery("SELECT c FROM Course c WHERE c.title = :title", Course.class)
//				.setParameter("title", title)
//				.getSingleResult();

		return entityManager.createQuery("SELECT c FROM Course c WHERE c.title = :title", Course.class)
				.setParameter("title", title).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Course> findCourseByTitleLike(String title) throws RemoteException {
		return entityManager.createNamedQuery("Course.findByTitleLike", Course.class)
				.setParameter("title", "%" + title + "%").getResultList();
	}

	@Override
	public List<Course> findAll() throws RemoteException {
		return entityManager.createNamedQuery("Course.findAll", Course.class).getResultList();
	}

	@Override
	public List<OnsiteCourse> findAllOnsiteCourses() throws RemoteException {
		return entityManager.createNamedQuery("Course.findOnsiteCourses", OnsiteCourse.class).getResultList();
	}

	@Override
	public Map<Course, Long> getCourseAndCountStudent() throws RemoteException {
		Map<Course, Long> map = new LinkedHashMap<>();

		String query = "SELECT s.course.id, count(*) as n " + "FROM StudentGrade s " + "GROUP BY s.course.id "
				+ "ORDER BY n DESC";

		List<?> list = entityManager.createQuery(query).getResultList();

		list.stream().map(o -> (Object[]) o).forEach(a -> {
			int courseId = (int) a[0];
			Course course = entityManager.find(Course.class, courseId);
			long count = (long) a[1];

			map.put(course, count);
		});

		return map;
	}

	@Override
	public Map<OnlineCourse, Long> getOnlineCourseAndCountStudent() throws RemoteException {
		Map<OnlineCourse, Long> map = new TreeMap<>(
				Comparator.comparing(Course::getCredits).reversed().thenComparing(Course::getTitle));
//Tạo một TreeMap để lưu trữ cặp giá trị OnlineCourse và số lượng sinh viên đăng ký khóa học.
		String query = "SELECT s.course.id, count(*) as n FROM  StudentGrade s GROUP BY s.course.id ";
		List<?> list = entityManager.createQuery(query).getResultList();

		list.stream().map(obj -> (Object[]) obj).forEach(item -> {
			int courseId = (int) item[0];
			OnlineCourse course = entityManager.find(OnlineCourse.class, courseId);
			long count = (long) item[1];
			if (course != null)
				map.put(course, count);
		});

		return map;
	}

}
