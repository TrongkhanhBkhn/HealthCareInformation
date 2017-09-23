package vn.com.daisy.form;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class FormDAO {

	public String getPathFromId(int formId){
		String result = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT htmlCode FROM Form WHERE formId = :formid";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("formid", formId);
			result = (String) query.uniqueResult();
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
		return result;
	}
	
	public String getPathFromName(String name){
		String result = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT htmlCode FROM Form WHERE name = :name";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setParameter("name", name);
			result = (String) query.uniqueResult();
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
		return result;
	}
}
