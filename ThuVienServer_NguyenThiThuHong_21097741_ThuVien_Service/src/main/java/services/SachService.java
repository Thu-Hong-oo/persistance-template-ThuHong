package services;

import java.time.Year;

import dao.SachDAO;
import entities.Sach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class SachService implements SachDAO {
	private EntityManager entityManager;

	public SachService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Sach findSachById(String id) {
		return entityManager.find(Sach.class, id);
	}

	@Override
	public boolean updateSach(Sach sach) {
	    // Kiểm tra năm xuất bản của sách cần cập nhật
	    int namXB = sach.getNamXB();
	    if (namXB > Year.now().getValue()) {
	        System.out.println("Không thể cập nhật sách với năm xuất bản sau năm hiện tại.");
	        return false;
	    }
		EntityTransaction trans = entityManager.getTransaction();
		try {
			trans.begin();
			entityManager.merge(sach);
			trans.commit();
			return true;
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

}
