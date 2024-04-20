package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import entities.Student;

public interface StudentDAO extends Remote {
	
	public boolean add(Student student) throws RemoteException;
	public List<Student> findAll() throws RemoteException;
	public List<Student> findByEnrollmentInYear(int year) throws RemoteException;
	public List<Student> findStudentsEnrolledInCourseByCourseTitle(String title) throws RemoteException;
	public Map<Student, Double> findStudentGPAs(int year) throws RemoteException; //Sorted by GPA
	public Map<Student, Double> findStudentMaxGPAs(int year) throws RemoteException; 
}
