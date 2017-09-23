package vn.com.daisy.dataelement;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class DataelementDAO {
	/*
	 * Hàm này được sử dụng để lấy tất cả các cateforyOptionsId từ dataelementId trong bảng DataElement_CategoryOptions
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getAllCategoryOptionIdFromId(int dataelementId){
		List<Integer> list = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT dc.id.categoryOptionId FROM DataElementCategoryOptions dc WHERE dc.id.dataElementId = :dataElementId";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("dataElementId", dataelementId);
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
		if(list.toString() == "[]")
			return null;
		return list;
	}
	
	public int getDEIdFromCategoryOptionId(int categoryOptionId){
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT dc.id.dataElementId FROM DataElementCategoryOptions dc WHERE dc.id.categoryOptionId = :categoryOptionId";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("categoryOptionId", categoryOptionId);
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
	 * @param categoryId
	 * @return
	 */
	/*
	 * 
	 */
	public String getNameCategoryOptionFromId(int categoryId)
	{
		String name = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT name FROM DataElementCategoryOption WHERE categoryOptionId = :id";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("id", categoryId);
			name = (String) query.uniqueResult();
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
		return name;
	}
	
	public int getCategoryOptionIdName(String name){
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT categoryOptionId FROM DataElementCategoryOption WHERE name = :name";
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
	
	public String getNameDEFromId(int dataelementId){
		String name = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "SELECT name FROM DataElement WHERE dataElementId = :id";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			query.setInteger("id", dataelementId);
			name = (String) query.uniqueResult();
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
		return name;
	}
	
	
}
