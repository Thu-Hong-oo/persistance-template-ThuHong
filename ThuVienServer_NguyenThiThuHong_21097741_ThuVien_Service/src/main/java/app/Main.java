package app;

import entities.Sach;
import jakarta.persistence.EntityManager;
import services.DocGiaService;
import services.EntityManagerFactoryUtil;
import services.SachService;

public class Main {
	public static void main(String[] args) {
		EntityManagerFactoryUtil entityManagerFactoryUtil = new EntityManagerFactoryUtil();
		EntityManager entityManager =  entityManagerFactoryUtil.getEntityManager();
		
		SachService sachService = new SachService(entityManager);
		DocGiaService docGiaService =  new DocGiaService(entityManager);
		
//		Sach sach = new Sach("S02", "abc", "abc", 2040, 333);
//		
//		boolean rs =  sachService.updateSach(sach);
//		System.out.println(rs);
		
		docGiaService.getDSDocGia("Cuoi Ky Java PT").forEach(System.out::println);
	}

}
