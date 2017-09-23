package vn.com.daisy.datavalue;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class DataValueDAO {
	public void addDataValue(DataValue datavalue) {
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(datavalue);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getDataValueFromOrgId(int orgId, int perioId) {
		List<String> list = new ArrayList<String>();
		String querySQL = "SELECT value FROM DataValue WHERE OrganitionUnitID= :id AND PeriodID = :periodId";
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("id", orgId);
			query.setInteger("periodId", perioId);
			list = (List<String>) query.list();
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<String> getDataValueFromPeriodId(int perioId) {
		List<String> list = new ArrayList<String>();
		String querySQL = "SELECT value FROM DataValue WHERE PeriodID = :periodId";
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("periodId", perioId);
			list = (List<String>) query.list();
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllDataValue(int id) {
		List<String> list = new ArrayList<String>();
		String querySQL = "SELECT value FROM DataValue WHERE DataElementId = :id";
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("id", id);
			list = (List<String>) query.list();
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}

	@SuppressWarnings("unchecked")	
	public List<String> getValueReport(int dataelementId, int orgId, int periodId) {
		List<String> list = new ArrayList<String>();
		String querySQL = "SELECT value FROM DataValue WHERE DataElementId = :dataelementId AND PeriodID= :periodId AND OrganitionUnitID= :orgId";
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("dataelementId", dataelementId);
			query.setInteger("periodId", periodId);
			query.setInteger("orgId", orgId);
			list = (List<String>) query.list();
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null){
				transaction.rollback();
				list = null;
			}
				
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}
	


	@SuppressWarnings("unchecked")	
	public List<Integer> getCategoryOptionId(int dataelementId, int orgId, int periodId) {
		List<Integer> list = new ArrayList<Integer>();
		String querySQL = "SELECT dv.id.categoryOptionId FROM DataValue dv WHERE dv.id.dataElementId = :dataelementId AND dv.id.periodId= :periodId AND dv.id.organitionUnitId= :orgId";
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("dataelementId", dataelementId);
			query.setInteger("periodId", periodId);
			query.setInteger("orgId", orgId);
			list = (List<Integer>) query.list();
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
		if (list.toString() == "[]")
			return null;

		return list;
	}
	public String getValueReport1(int dataelementId, int orgId, int periodId, int categoryOptionId) {
		String result = null;
		String querySQL = "SELECT value FROM DataValue WHERE DataElementId = :dataelementId AND PeriodID= :periodId AND OrganitionUnitID= :orgId AND CategoryOptionID= :categoryId";
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("dataelementId", dataelementId);
			query.setInteger("periodId", periodId);
			query.setInteger("orgId", orgId);
			query.setInteger("categoryId", categoryOptionId);
			result = (String) query.uniqueResult();
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
		if (result == null)
			return "0";

		return result;
	}
	

}
