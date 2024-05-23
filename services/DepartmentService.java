package services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.DepartmentDAO;
import entities.Department;
import jakarta.persistence.EntityManager;

public class DepartmentService implements DepartmentDAO {

	private EntityManager entityManager;

	public DepartmentService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Map<Department, Long> countCoursesByDepartment() {
	    // Tạo một LinkedHashMap để lưu trữ kết quả. LinkedHashMap duy trì thứ tự của các phần tử theo thứ tự chèn vào.
		Map<Department, Long> map = new LinkedHashMap<Department, Long>();
	    // Thực thi truy vấn được định nghĩa sẵn để lấy danh sách kết quả.
		List<?> results = entityManager.createNamedQuery("Department.countCoursesByDepartment").getResultList();
	    // Sử dụng stream để xử lý kết quả trả về từ truy vấn.
		results.stream().map(obj -> (Object[]) obj).forEach(obj -> {// ép kiểu đối tượng snag mảng đối tượng
	        // Chuyển đổi từng kết quả (dạng Object[]) sang các giá trị cụ thể.
			int departmentID = (int) obj[0];// Lấy ID của khoa.
			Department department = entityManager.find(Department.class, departmentID);// Tìm đối tượng Department từ ID.
			long count = (long) obj[1];// Lấy số lượng khóa học.
			map.put(department, count);
		});

		return map;
	}

}
