package dao;

import java.util.List;

import entities.DocGia;

public interface DocGiaDAO {
	public DocGia findDocGiaByID(String id);
	
	public List<DocGia> getDSDocGia(String tuaSach);

}
