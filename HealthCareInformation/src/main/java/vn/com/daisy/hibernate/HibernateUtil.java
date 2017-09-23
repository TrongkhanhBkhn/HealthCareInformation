package vn.com.daisy.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;

	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
			
				sessionFactory = new Configuration().configure("/vn/com/daisy/hibernateconfig/hibernate.cfg.xml").buildSessionFactory();
				System.err.println("Khanh" +" OK");
			} catch (Exception ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("sessionFactory error " + ex);
				System.err.println("Khanh" +" Khog the connet");
			}
		}

		return sessionFactory;
	}

	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public static Session openSession() {
		return getSessionFactory().openSession();
	}

}