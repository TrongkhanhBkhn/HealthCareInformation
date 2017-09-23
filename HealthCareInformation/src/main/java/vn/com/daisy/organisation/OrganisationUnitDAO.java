package vn.com.daisy.organisation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class OrganisationUnitDAO {
	public int getLevelFormOrgId(int orgId) {
		int level = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("SELECT level FROM OrgUnitStructure WHERE OrganisationUnitID = :orgId");
			query.setInteger("orgId", orgId);
			level = (Integer) query.uniqueResult();
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
		return level;
	}

	@SuppressWarnings("unused")
	public int getParentIdFromOrgId(int orgId) {
		String name = null;
		int parentId = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session
					.createQuery("SELECT parentId FROM OrganisationUnit WHERE OrganisationUnitID = :orgId");
			query.setInteger("orgId", orgId);
			parentId = (Integer) query.uniqueResult();
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
		return parentId;
	}
	/**
	 * 
	 * @param orgId
	 * @return
	 */

	public String getNameOrgFromId(int orgId) {
		String name = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("SELECT name FROM OrganisationUnit WHERE OrganisationUnitID = :orgId");
			query.setInteger("orgId", orgId);
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
	
	public int getOrgIdFromName(String name, int parentId){
		int result = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("SELECT organisationUnitId FROM OrganisationUnit WHERE name = :name AND parentId= :parentId");
			query.setParameter("name", name);
			query.setInteger("parentId", parentId);
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
	public List<Integer> getChildrenIdFormParentID(int parentId, int orgGroupId) {
		List<Integer> orgs = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(
					"SELECT ou.organisationUnitId FROM OrganisationUnit ou, OrgUnitGroupMembers oum WHERE ou.parentId = :parentID AND oum.id.orgUnitGroupId = :orgGroupId AND ou.organisationUnitId = oum.id.organisationUnitId");
			query.setInteger("parentID", parentId);
			query.setInteger("orgGroupId", orgGroupId);
			orgs = (List<Integer>) query.list();
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
		if (orgs.toString() == "[]")
			return null;
		return orgs;
	}

/**
 * 
 * @param parentId
 * @param orgGroupId
 * @return
 */
	@SuppressWarnings("unchecked")
	public List<String> getChildrenFormParentID(int parentId, int orgGroupId) {
		List<String> orgs = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(
					"SELECT ou.name FROM OrganisationUnit ou, OrgUnitGroupMembers oum WHERE ou.parentId = :parentID AND oum.id.orgUnitGroupId = :orgGroupId AND ou.organisationUnitId = oum.id.organisationUnitId");
			query.setInteger("parentID", parentId);
			query.setInteger("orgGroupId", orgGroupId);
			orgs = (List<String>) query.list();
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
		if (orgs.toString() == "[]")
			return null;
		return orgs;
	}
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<String> getAllChildrenFormParentID(int parentId) {
		List<String> orgs = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(
					"SELECT name FROM OrganisationUnit WHERE parentId = :parentID");
			query.setInteger("parentID", parentId);
			orgs = (List<String>) query.list();
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
		if (orgs.toString() == "[]")
			return null;
		return orgs;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getAllChildrenIdFormParentID(int parentId) {
		List<Integer> orgs = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(
					"SELECT organisationUnitId FROM OrganisationUnit WHERE parentId = :parentID");
			query.setInteger("parentID", parentId);
			orgs = (List<Integer>) query.list();
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
		if (orgs.toString() == "[]")
			return null;
		return orgs;
	}

}
