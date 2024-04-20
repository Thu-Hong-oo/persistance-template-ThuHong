package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.DepartmentDAO;
import entities.Department;
import jakarta.persistence.EntityManager;

public class DepartmentService extends UnicastRemoteObject implements DepartmentDAO {

	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public DepartmentService(EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public Map<Department, Long> countCoursesByDepartment() throws RemoteException {

		Map<Department, Long> map = new LinkedHashMap<Department, Long>();

		List<?> results = entityManager.createNamedQuery("Department.countCoursesByDepartment").getResultList();

		results.stream().map(obj -> (Object[]) obj).forEach(obj -> {

			int departmentID = (int) obj[0];

			Department department = entityManager.find(Department.class, departmentID);

			long count = (long) obj[1];

			map.put(department, count);

		});

		return map;
	}

	@Override
	public List<Department> findDepartmentNotOwnerCourse() throws RemoteException {
		return entityManager.createNamedQuery("Department.findDepartmentNotOwnerCourse", Department.class)
				.getResultList();
	}
}
