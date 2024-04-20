package school_app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import services.CourseService;
import services.EntityManagerFactoryUtil;

public class CourseServiceTest {
	private static EntityManagerFactoryUtil mangerFactoryUtil;
	private static EntityManager entityManager;

	@BeforeAll
	static void setup() {
		mangerFactoryUtil = new EntityManagerFactoryUtil();
		entityManager = mangerFactoryUtil.getEnManager();
	}

	@Test
	@DisplayName("findCourseByID")
	void findCourseByID() throws RemoteException {
		CourseService courseService = new CourseService(entityManager);
		System.out.println(courseService.findCourseByID(1));
		assertNotNull(courseService.findCourseByID(1));
	}

	@AfterAll
	public static void afterAll() {
		mangerFactoryUtil.closeEnManager();
		mangerFactoryUtil.closeEnManagerFactory();
	}
}
