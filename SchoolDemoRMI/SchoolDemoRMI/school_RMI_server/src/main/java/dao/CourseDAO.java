package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import entities.Course;
import entities.OnlineCourse;
import entities.OnsiteCourse;

public interface CourseDAO extends Remote {
	public boolean addCourse(Course course) throws RemoteException;
	public boolean updateCourse(Course course) throws RemoteException;
	public boolean deleteCourse(int id) throws RemoteException;
	public Course findCourseByID(int id) throws RemoteException;
	public Course findCourseByTitle(String title) throws RemoteException;
	public List<Course> findCourseByTitleLike(String title) throws RemoteException;
	public List<Course> findAll() throws RemoteException; 
	public List<OnsiteCourse> findAllOnsiteCourses() throws RemoteException;
	public Map<Course, Long> getCourseAndCountStudent() throws RemoteException;
	public Map<OnlineCourse, Long> getOnlineCourseAndCountStudent() throws RemoteException;
}
