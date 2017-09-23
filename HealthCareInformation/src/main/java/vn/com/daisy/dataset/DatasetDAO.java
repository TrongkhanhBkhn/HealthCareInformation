package vn.com.daisy.dataset;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class DatasetDAO {
	/*
	 * Hàm này được sử dụng để lấy toàn bộ thông tin tên dataset từ bảng Dataset
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAllNameDataset() {
		List<Object> list = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT name FROM Dataset";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			list = (List<Object>) query.list();
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
		if (list.toString() == "[]") {
			return null;
		}
		return list;
	}

	public void addDataset(Object dataset) {
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(dataset);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}
	}

	/*
	 * Hàm này được dùng để lấy datasetId từ organisation id từ bảng
	 * DataSetSource
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getDatasetIdFromOrgId(int orgId) {
		List<Integer> list = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT us.id.datasetId FROM DataSetSource us WHERE us.id.organitionUnitId = :orgId";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("orgId", orgId);
			list = (List<Integer>) query.list();
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
		if (list.toString() == "[]") {
			return null;
		}

		return list;
	}

	/*
	 * Hàm này được dùng để lấy thông ti tên dataset từ bảng dataset
	 */
	public String getDatasetNameFromId(int datasetId) {
		String result = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT name FROM Dataset WHERE datasetId = :id";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("id", datasetId);
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

	/*
	 * Hàm này được dùng để lấy form id từ thông tin tên dataset trong bảng
	 * Dataset
	 */
	public int getFormIdFromDatasetName(String name) {
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySql = "SELECT dataEntryForm FROM Dataset WHERE name = :name";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySql);
			query.setParameter("name", name);
			result = (Integer) query.uniqueResult();
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

	public int getDatasetNameFormName(String name) {
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT datasetId FROM Dataset WHERE name = :name";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setParameter("name", name);
			result = (Integer) query.uniqueResult();
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

	@SuppressWarnings("unchecked")
	public List<Integer> getAllDEIdFromId(int datasetId) {
		List<Integer> list = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT dm.id.dataElementId FROM DataSetMember dm WHERE dm.id.datasetId = :datasetId";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("datasetId", datasetId);
			list = (List<Integer>) query.list();
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
		if (list.toString() == "[]")
			return null;
		return list;
	}

	/*
	 * Hàm này được sử dụng để lấy toàn bộ thông ti organisation id từ bảng
	 * DataserSource
	 */
	public int getOrgIdFromId(int datasetId) {
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT ds.id.organitionUnitId FROM DataSetSource ds WHERE ds.id.datasetId = :datasetId";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("datasetId", datasetId);
			result = (Integer) query.uniqueResult();
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
	
	

	/**
	 * 
	 * @param name
	 * @return
	 */
	public int getDatasetIdFromName(String name) {
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT datasetId FROM Dataset WHERE name = :name";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setParameter("name", name);
			result = (Integer) query.uniqueResult();
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
	
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public int getOrgGroupIdFromName(String name) {
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT orgUnitGroupId FROM Dataset WHERE name = :name";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setParameter("name", name);
			result = (Integer) query.uniqueResult();
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
