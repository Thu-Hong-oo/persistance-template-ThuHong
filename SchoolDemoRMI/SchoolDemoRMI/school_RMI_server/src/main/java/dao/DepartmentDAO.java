package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import entities.Department;


public interface DepartmentDAO extends Remote {
	// Count Course by Department
	public Map<Department, Long> countCoursesByDepartment() throws RemoteException;
	
	public List<Department> findDepartmentNotOwnerCourse() throws RemoteException;
}
