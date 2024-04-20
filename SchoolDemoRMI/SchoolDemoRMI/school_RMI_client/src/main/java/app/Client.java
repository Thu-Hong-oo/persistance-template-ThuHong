package app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.StudentDAO;


public class Client {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, ClassNotFoundException {
		// Way 1: Getting the registry
//		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2005);
//		CourseDAO courseDAO = (CourseDAO) registry.lookup("courseDAO");
//		StudentDAO studentDAO = (StudentDAO) registry.lookup("studentDAO");
//		DepartmentDAO departmentDAO = (DepartmentDAO) registry.lookup("departmentDAO");
		
		// Way 2
		String URL = "rmi://127.0.0.1:3000/";
		CourseDAO courseDAO = (CourseDAO) Naming.lookup(URL + "courseDAO");
		StudentDAO studentDAO = (StudentDAO) Naming.lookup(URL + "studentDAO");
		DepartmentDAO departmentDAO = (DepartmentDAO) Naming.lookup(URL + "departmentDAO");

		departmentDAO.findDepartmentNotOwnerCourse().forEach(x -> System.out.println(x));

		studentDAO.findAll().forEach(x -> System.out.println(x));

	}

}
