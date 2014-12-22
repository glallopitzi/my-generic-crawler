package org.appnest.databuilder;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DataBuilderConnectionManager {

	private static SessionFactory factory;
	
	@SuppressWarnings("deprecation")
	public DataBuilderConnectionManager() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object."
					+ ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	
	public SessionFactory getFactory() {
		return factory;
	}

	public static void setFactory(SessionFactory factory) {
		DataBuilderConnectionManager.factory = factory;
	}
}
