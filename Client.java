package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import entities.DocGia;
import entities.Sach;

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
					System.out.println("Enter your choice: \n" + "1. Insert new ChiTietMuonSach: \n"
							+ "2. Update Sach: \n" + "3. Find all DocGia have borrow Sach greater and equal 2 \n");
					choice = sc.nextInt();
					out.writeInt(choice);
					out.flush();

					switch (choice) {
				
					case 1:
						sc.nextLine();
						
						break;

					case 2:
						sc.nextLine();
				
						break;

					case 3:
						sc.nextLine(); 
						
						break;
					default:
						System.out.println("Please choice");
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
