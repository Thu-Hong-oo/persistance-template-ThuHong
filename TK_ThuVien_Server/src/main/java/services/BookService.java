package services;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.BookDAO;
import entities.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookService implements BookDAO {

	private EntityManager entityManager;

	public BookService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Book> listBooks(String section, String fromReturnDate, String toReturnDate) {
		LocalDate fromDate = LocalDate.parse(fromReturnDate);
	    LocalDate returnDate = LocalDate.parse(toReturnDate);
		String query = "SELECT DISTINCT b " +
                "FROM Book b " +
                "JOIN b.location l " +
                "JOIN b.borrowDetails bd " +  
                "JOIN bd.borrow bo " +
                "WHERE l.section LIKE :section " +
                "AND bo.returnDate BETWEEN :startDate AND :endDate";
		return entityManager.createQuery(query, Book.class).setParameter("section", "%" + section + "%")
				.setParameter("startDate", fromDate).setParameter("endDate", returnDate).getResultList();

	}

	@Override
	public Map<String, Integer> getNoOfBookByStudent() {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		String query = "SELECT  CONCAT(s.firstName, ' ', s.lastName) as name, count(distinct bd.book) as total from BorrowDetail bd INNER JOIN bd.borrow bo INNER JOIN bo.student s GROUP BY name ORDER BY total DESC ";
		List<?> results = entityManager.createQuery(query).getResultList();
		
		results.stream().map(obj ->(Object[]) obj).forEach(obj -> {
			String name = (String) obj[0];
			Integer total =  ((Number) obj[1]).intValue();
			map.put(name, total);
		});
		return map;
	}

	@Override
	public boolean updateTitle(String isbn, String newTitle) {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			String query = "select b from Book b where b.isbn = :isbn ";
			Book book = entityManager.createQuery(query,Book.class).setParameter("isbn", isbn).getSingleResult();
			book.setTitle(newTitle);
			entityManager.merge(book);
			transaction.commit();
			return true;
		} catch (Exception e) {
		e.printStackTrace();
		}
		return false;
	}

}
