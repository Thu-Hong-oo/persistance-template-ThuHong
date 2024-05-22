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

public class ThuVienClient {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException {
		try (Scanner sc = new Scanner(System.in)) {
			try (Socket socket = new Socket("localhost", 8888)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());// dùng để gửi từ client đến
																							// server
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());// nhận

				int choice = 0;

				while (choice != -1) {
					System.out.println("Enter your choice: \n" + "1. Insert new ChiTietMuonSach: \n"
							+ "2. Update Sach: \n" + "3. Find all DocGia have borrow Sach greater and equal 2 \n-1. Exit");
					choice = sc.nextInt();
					out.writeInt(choice);
					out.flush();

					switch (choice) {
					case -1:
						break;
					case 1:
						sc.nextLine();
						System.out.println("Enter DocGia ID");
						String docGiaId = sc.nextLine();
						out.writeObject(docGiaId);
						System.out.println(in.readObject().toString());
						System.out.println("Enter Sach id:");
						String sachId = sc.nextLine();
						out.writeObject(sachId);
						System.out.println(in.readObject().toString());
						out.flush();
						boolean addChiTietMuonSach = in.readBoolean();
						System.out.println("add result: " + addChiTietMuonSach);
						break;

					case 2:
						sc.nextLine();
						System.out.println("Enter Sach ID:");
						String sachIdToUpdate = sc.nextLine();
						System.out.println("Enter new TuaSach:");
						String tuaSach = sc.nextLine();
						System.out.println("Enter new TacGia:");
						String tacGia = sc.nextLine();
						System.out.println("Enter new NamXB:");
						int namXB = sc.nextInt();
						System.out.println("Enter new GiaBia:");
						long giaBia = sc.nextLong();

						Sach sachToUpdate = new Sach(sachIdToUpdate, tuaSach, tacGia, namXB, giaBia);

						out.writeObject(sachToUpdate);
						out.flush();

						boolean updateResult = in.readBoolean();
						System.out.println("Update result: " + updateResult);
						break;

					case 3:
						sc.nextLine(); // Đọc kí tự newline còn lại sau khi nhập số
						System.out.println("Enter TuaSach:");
						String tuaSachForList = sc.nextLine();

						try {
							out.writeObject(tuaSachForList);
							out.flush();

							List<DocGia> readersList = (List<DocGia>) in.readObject();

							readersList.forEach(System.out::println);

						} catch (Exception e) {
							e.printStackTrace();
						}
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
