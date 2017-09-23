package vn.com.daisy.translation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.com.daisy.hibernate.HibernateUtil;

public class TranslationDAO {

	public void addTranslation(Translation trans) {
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			session.save(trans);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
	}
public String getOrginalValue(String objectclass, String locale, String value){
	String translation = null;
	Transaction trns = null;
	Session session = null;
	session = HibernateUtil.openSession();
	try {
		trns = session.beginTransaction();
		Query query = session
				.createQuery("SELECT objectProperty FROM Translation WHERE ObjectClass = :objectClass AND Locale = :locale AND value = :value");
		query.setParameter("objectClass", objectclass);
		query.setParameter("locale", locale);
		query.setParameter("value", value);
		translation = (String) query.uniqueResult();
		trns.commit();
	} catch (RuntimeException e) {
		if (trns != null) {
			trns.rollback();
		}
		e.printStackTrace();
	} finally {
		session.flush();
		session.close();
	}
	return translation;
}
	
	public String getTranslation(String objectclass, String locale, String objectPro) {
		String translation = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session
					.createQuery("SELECT value FROM Translation WHERE ObjectClass = :objectClass AND Locale = :locale AND ObjectProperty = :objectProperty");
			query.setParameter("objectClass", objectclass);
			query.setParameter("locale", locale);
			query.setParameter("objectProperty", objectPro);
			translation = (String) query.uniqueResult();
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
				translation = null;
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return translation;
	}
	@SuppressWarnings("unchecked")
	public List<String> getAllObjectValue(String objectclass, String locale){
		 List<String> translations = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session
					.createQuery("SELECT objectProperty FROM Translation WHERE ObjectClass = :objectClass AND Locale = :locale");
			query.setParameter("objectClass", objectclass);
			query.setParameter("locale", locale);
			translations = (List<String>) query.list();
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		if(translations.toString() =="[]")
			return null;
		return translations;
	}
	

}
