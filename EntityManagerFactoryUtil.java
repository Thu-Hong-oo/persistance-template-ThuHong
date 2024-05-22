package services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryUtil {
	private EntityManagerFactory entityManagerFactory;
	 private EntityManager entityManager;
	 
	 public EntityManagerFactoryUtil() {
		 this.entityManagerFactory= Persistence.createEntityManagerFactory("MSSQL");
		 this.entityManager = entityManagerFactory.createEntityManager();
	 }
	 public EntityManager getEntityManager() {
		 return this.entityManager;
	 }
	 public void closeEntityManager() {
		 this.entityManager.close();
	 }
	 public void closeEntityManagerFactory() {
		 this.entityManagerFactory.close();
	 }

}
