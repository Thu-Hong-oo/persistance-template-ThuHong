package dao;

import entities.DocGia;
import entities.Sach;

public interface SachDAO {
	public Sach findSachById(String id);
	public boolean updateSach(Sach sach);
}
