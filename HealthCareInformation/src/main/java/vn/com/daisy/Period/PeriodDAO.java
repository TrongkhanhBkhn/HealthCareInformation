package vn.com.daisy.Period;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class PeriodDAO {
	public int getPeriodIdFromPeriodTypeID(int periodTypeID) {
		int peridId = 0;
		String querySQL = "SELECT periodId FROM Period WHERE PeriodTypeID = :periodTypeID";
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("periodTypeID", periodTypeID);
			peridId = (Integer) query.uniqueResult();
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

		return peridId;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getAllNamePeriodType() {
		List<Object> list = new ArrayList<>();
		String querySQL = "SELECT name FROM PeriodType";
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			list = (List<Object>) query.list();
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}


	public int getPeriodTypeID(String name) {
		int periodTypeID = 0;
		String querySQL = "SELECT periodTypeId FROM PeriodType WHERE Name = :name";
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setParameter("name", name);
			periodTypeID = (Integer) query.uniqueResult();
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
		return periodTypeID;
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getAllPeriodId(){
		List<Integer> list = new ArrayList<>();
		String querySQL = "SELECT periodId FROM Period";
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			list = (List<Integer>) query.list();
			trns.commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}

}
