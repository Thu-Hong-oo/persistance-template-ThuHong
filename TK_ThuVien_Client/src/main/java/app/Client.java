package app;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entities.Book;

public class Client {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException {
		try (Scanner sc = new Scanner(System.in)) {
			try (Socket socket = new Socket("localhost", 8888)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());// dùng để gửi từ client đến
																							// server
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());// nhận

				int choice = 0;

				while (true) {
					System.out.println("Enter your choice: \n" + "1. Get List Books are borrowed by section like: \n"
							+ "2. get no of books by student: \n" + "3. Update title of book by isbn \n");
					choice = sc.nextInt();
					out.writeInt(choice);
					out.flush();

					switch (choice) {

					case 1:
						sc.nextLine();
						System.out.println("Enter section:" );
						String section = sc.nextLine();
						out.writeObject(section);
						System.out.println("Enter start date");
						String startDate = sc.nextLine();
						out.writeObject(startDate);
						System.out.println("Enter end date");
						String endDate = sc.nextLine();
						out.writeObject(endDate);
						
						List<Book> listBook = (List<Book>)in.readObject();
						listBook.forEach(System.out::println);
						break;

					case 2:
						sc.nextLine();
						Map<String, Integer> map = (Map<String, Integer>) in.readObject();
						map.entrySet().stream().forEach(entry->{
							System.out.println(entry.getKey()+":"+entry.getValue());
						});

						break;

					case 3:
						sc.nextLine(); 
						System.out.println("Enter ISBN: ");
						String isbn = sc.nextLine();
						out.writeObject(isbn);
						System.out.println("Enter new title: ");
						String newTitle = sc.nextLine();
						out.writeObject(newTitle);
						out.flush();
						System.out.println(in.readBoolean());
						break;
					default:
						System.out.println("Invalid choice");
						break;
					}
				}

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
