package services;

import java.util.List;

import dao.DocGiaDAO;
import entities.DocGia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DocGiaService implements DocGiaDAO {
	private EntityManager entityManager;

	public DocGiaService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public DocGia findDocGiaByID(String id) {
		return entityManager.find(DocGia.class, id);
	}

	@Override
	public List<DocGia> getDSDocGia(String tuaSach) {
		String jpql = "SELECT DISTINCT c.docGia " + "FROM ChiTietMuonSach c " + "JOIN c.sach s "
				+ "WHERE s.tuaSach = :tuaSach " + "GROUP BY c.docGia " + "HAVING COUNT(c) >= 2";

		return entityManager.createQuery(jpql, DocGia.class).setParameter("tuaSach", tuaSach).getResultList();

	}

}
