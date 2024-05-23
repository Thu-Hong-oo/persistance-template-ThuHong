package testThuVien;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import entities.Book;
import jakarta.persistence.EntityManager;
import services.BookService;
import services.EntityManagerFactoryUtil;

public class TestThuVien {
	private static EntityManagerFactoryUtil managerFactoryUtil;
	private static EntityManager entityManager;
	private static BookService bookService;

	@BeforeAll
	static void setup() {
		managerFactoryUtil = new EntityManagerFactoryUtil();
		entityManager = managerFactoryUtil.getEntityManager();
		bookService = new BookService(entityManager);
	}

	@Test
	void testListbook() {
		String section = "Bound";
		String fromDate = "2022-04-29";
		String returnDate = "2022-05-01";
		List<Book> books = bookService.listBooks(section, fromDate, returnDate);
		books.forEach(System.out::println);
		assertNotNull(books);
	}
	
	@Test
	void testgetNoOfBookByStudent() {
		  Map<String, Integer> result = bookService.getNoOfBookByStudent();
	        result.forEach((name, total) -> System.out.println(name + ": " + total));
	}
	
	@Test 
	void updateBook() {
		String isbn = "23755004321";
		String newTitle = "abc";
		boolean result = bookService.updateTitle(isbn, newTitle);
		assertTrue(result);
	}

}
