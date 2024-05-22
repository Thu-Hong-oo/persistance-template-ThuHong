package testThuVien;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entities.DocGia;
import entities.Sach;
import jakarta.persistence.EntityManager;
import services.ChiTietMuonSachService;
import services.DocGiaService;
import services.EntityManagerFactoryUtil;
import services.SachService;

class TestThuVien {
	private static EntityManagerFactoryUtil managerFactoryUtil;
	private static EntityManager entityManager;
	private static ChiTietMuonSachService chiTietMuonSachService;
	private static DocGiaService docGiaService;
	private static SachService sachService;

	@BeforeAll
	static void setup() {
		managerFactoryUtil = new EntityManagerFactoryUtil();
		entityManager = managerFactoryUtil.getEntityManager();
		chiTietMuonSachService = new ChiTietMuonSachService(entityManager);
		docGiaService = new DocGiaService(entityManager);
		sachService = new SachService(entityManager);
	}

	@Test
	@DisplayName("testAddUser")
	void testAddChiTietMuonSach() {
		Sach sach = entityManager.find(Sach.class, "S01");
		DocGia docGia = entityManager.find(DocGia.class, "DG02");
		boolean result = chiTietMuonSachService.themChiTietMuonSach(docGia, sach);
		assertTrue(result);
	}
	
	@Test
	@DisplayName("testUpdateSach")
	void testUpdateSach()
	{
		Sach sach = new Sach("S02", "abc", "abc", 2011, 333);
		assertTrue(sachService.updateSach(sach));
	}
	
	@Test
	void testGetDsDG() {
		 // Call the method with the book title
        List<DocGia> result = docGiaService.getDSDocGia("Cuoi ky Java PT");

        // Verify the size of the result list
        assertEquals(2, result.size(), "Expected 2 readers who borrowed the book at least twice");

        // Create a set of expected reader names
        Set<String> expectedNames = new HashSet<>();
        expectedNames.add("Nguyen Van B");
        expectedNames.add("Nguyen Van C");

        // Create a set of actual reader names from the result
        Set<String> actualNames = result.stream()
                                        .map(DocGia::getHoTenDG)
                                        .collect(Collectors.toSet());

        // Verify that the actual reader names match the expected names
        assertEquals(expectedNames, actualNames, "The readers' names should match the expected values");
	}
	@AfterAll
	public static void afterAll() {
		managerFactoryUtil.closeEntityManagerFactory();
		managerFactoryUtil.closeEntityManager();
	}

}
