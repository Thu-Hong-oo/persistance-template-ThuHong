package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.print.Doc;

import entities.ChiTietMuonSach;
import entities.DocGia;
import entities.Sach;
import jakarta.persistence.EntityManager;
import services.ChiTietMuonSachService;
import services.DocGiaService;
import services.EntityManagerFactoryUtil;
import services.SachService;

public class Server_ThuVien {
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
	private ChiTietMuonSachService chiTietMuonSachService;
	private DocGiaService docGiaService;
	private SachService sachService;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.managerFactoryUtil = new EntityManagerFactoryUtil();
		this.entityManager = managerFactoryUtil.getEntityManager();
		this.chiTietMuonSachService = new ChiTietMuonSachService(entityManager);
		this.docGiaService = new DocGiaService(entityManager);
		this.sachService = new SachService(entityManager);

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
					case 1: // add new ChiTietMuonSach
						String docGiaId = (String) in.readObject();         
                        DocGia docGia = docGiaService.findDocGiaByID(docGiaId);
                        if (docGia == null) {
                            out.writeObject("DocGia not found");
                            out.flush();
                            continue;
                        }
                        out.writeObject(docGia);
                        String sachId = (String) in.readObject();
                        Sach sach = sachService.findSachById(sachId);
                        if (sach == null) {
                            out.writeObject("Sach not found");
                            out.flush();
                            continue;
                        }
                        out.writeObject(sach);
						boolean result = chiTietMuonSachService.themChiTietMuonSach(docGia, sach);
						out.writeBoolean(result);
						out.flush();
						break;
					case 2: // Update Sach
						sach = (Sach) in.readObject();
						result = sachService.updateSach(sach);
						out.writeBoolean(result);
						out.flush();
						break;
					case 3:// get docGia List
						 try {
						        String tuaSach = (String) in.readObject();
						        List<DocGia> docGiaList = docGiaService.getDSDocGia(tuaSach);
						        out.writeObject(docGiaList);
						        out.flush();
						    } catch (Exception e) {
						        e.printStackTrace();
						    }
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
