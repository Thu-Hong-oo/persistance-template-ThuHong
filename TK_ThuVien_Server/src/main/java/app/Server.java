package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import entities.Book;
import jakarta.persistence.EntityManager;
import services.BookService;
import services.EntityManagerFactoryUtil;

public class Server {
	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(8888);) {
			System.out.println("Server started on port 8888");

			ExecutorService executorService = Executors.newCachedThreadPool();

			while (true) {
				Socket clientSocket = server.accept();
				executorService.execute(new ClientHandler(clientSocket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class ClientHandler implements Runnable {
	private Socket clientSocket;
	private EntityManagerFactoryUtil managerFactoryUtil;
	private EntityManager entityManager;
	private BookService bookService;


	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.managerFactoryUtil = new EntityManagerFactoryUtil();
		this.entityManager = managerFactoryUtil.getEntityManager();
		bookService = new BookService(entityManager);


	}

	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());//Được sử dụng để đọc dữ liệu từ client gửi đến server qua socket.
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());//gửi

			int choice = 0;
			{
				while (true) {
					choice = in.readInt();

					switch (choice) {
					case 1: 
						String section = (String) in.readObject();
//						System.out.println("Client requested "+section);
						String startDate = (String)in.readObject();
//						System.out.println("Client requested "+startDate);
						String endDate = (String)in.readObject();
//						System.out.println("Client requested "+endDate);
						
						List<Book> books = bookService.listBooks(section, startDate, endDate);
						out.writeObject(books);
						out.flush();
						break;
					case 2: 
						Map<String, Integer> map = bookService.getNoOfBookByStudent();
						out.writeObject(map);
						out.flush();	
						break;
					case 3:
						String isbn = (String) in.readObject();
						String newTitle = (String) in.readObject();
						boolean result = bookService.updateTitle(isbn, newTitle);
						out.writeBoolean(result);
						out.flush();
						break;

					default:
						out.writeObject("Please choose 1,2,3");
						out.flush();
			
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.managerFactoryUtil.closeEntityManager();
			this.managerFactoryUtil.closeEntityManagerFactory();
		}

	}

}