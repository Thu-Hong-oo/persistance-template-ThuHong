package services;

import java.util.Date;

import dao.ChiTietMuonSachDAO;
import entities.ChiTietMuonSach;
import entities.DocGia;
import entities.Sach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ChiTietMuonSachService implements ChiTietMuonSachDAO {
	private EntityManager entityManager;

	public ChiTietMuonSachService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public boolean themChiTietMuonSach(DocGia docGia, Sach sach) {
		EntityTransaction transaction = entityManager.getTransaction();
	try {
		transaction.begin();
		
		Date ngayMuon = new Date();
		Date ngayTra = null;
		long tienCoc;
		
		if(sach.getNamXB()>2015) {
			tienCoc = sach.getGiaBia();
		}
		else {
			tienCoc = sach.getGiaBia()/2;
		}
		ChiTietMuonSach  chiTietMuonSach = new ChiTietMuonSach(ngayMuon, ngayTra,tienCoc,sach,docGia);
		
		
		entityManager.persist(chiTietMuonSach);
		transaction.commit();
		return true;
	} catch (Exception e) {
		if(transaction.isActive()) {
			transaction.rollback();
		}
		e.printStackTrace();
	}
	return false;
	}

}
