package com.cts.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static final String persistence_unit = "TestPersistence";
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEntityManagerFactory() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory(persistence_unit);
		}
		return emf;
	}
	public static void shutdown() {
		if(emf != null) {
			emf.close();
		}
	}

}
