package dao;

import java.util.List;
import java.util.Map;

import entities.Book;

public interface BookDAO {
	public List<Book> listBooks(String section, String fromReturnDate, String toReturnDate);
	
	public Map<String, Integer> getNoOfBookByStudent();
	
	public boolean updateTitle(String isbn, String newTitle);

}
