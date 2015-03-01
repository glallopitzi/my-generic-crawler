package org.appnest.databuilder;

import javax.security.auth.login.Configuration;

import org.eclipse.jetty.websocket.common.SessionFactory;

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
