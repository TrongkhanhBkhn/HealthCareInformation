package vn.com.daisy.user;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import vn.com.daisy.hibernate.HibernateUtil;

public class UserDAO {
	public UserDAO() {

	}

	public void addUser(Object user) {
		Transaction transaction = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			transaction = session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.flush();
			session.close();
		}

	}
	public void deleteUser(User user){
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();

			session.delete(user);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
	public void deleteUserInfo(UserInfo user){
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();

			session.delete(user);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	public void updateUser(User user) {
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();

			session.update(user);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
	public int getOrgIdFromUserId(int userId){
		int orgId = 0;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try{
			trns = session.beginTransaction();
			String querySQL ="SELECT organisationUnitId FROM UserInfo WHERE UserInfoID= :userInfoId";
			Query query = session.createQuery(querySQL);
			query.setInteger("userInfoId", userId);
			orgId =(Integer)query.uniqueResult();
			trns.commit();
		}catch (RuntimeException e){
			if (trns != null) {
				trns.rollback();
			}
		}finally {
			session.flush();
			session.close();
		}
		return orgId;
	}	

public boolean getStatusUser(String username){
	boolean status = false;
	Transaction trns = null;
	Session session = null;
	session = HibernateUtil.openSession();
	try{
		trns = session.beginTransaction();
		String querySQL ="SELECT disabled FROM User WHERE UserName= :userName";
		Query query = session.createQuery(querySQL);
		query.setParameter("userName", username);
		status =(Boolean)query.uniqueResult();
		trns.commit();
	}catch (RuntimeException e){
		if (trns != null) {
			trns.rollback();
		}
	}finally {
		session.flush();
		session.close();
	}
	return status;
}
public int getUserIdFromUserName(String userName){
	int userId = 0;
	Transaction trns = null;
	Session session = null;
	session = HibernateUtil.openSession();
	try{
		trns = session.beginTransaction();
		String querySQL ="SELECT userId FROM User WHERE UserName= :userName";
		Query query = session.createQuery(querySQL);
		query.setParameter("userName", userName);
		userId =(Integer)query.uniqueResult();
		trns.commit();
	}catch (RuntimeException e){
		if (trns != null) {
			trns.rollback();
		}
	}finally {
		session.flush();
		session.close();
	}
	return userId;
}
public UserInfo getUserInfor(int userId){
	UserInfo userInfo = null;
	Transaction trns = null;
	Session session = null;
	session = HibernateUtil.openSession();
	try {
		trns = session.beginTransaction();
		Query query = session.createQuery("FROM UserInfo WHERE UserInfoID= :userId");
		query.setInteger("userId", userId);
		userInfo = (UserInfo) query.uniqueResult();
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
	return userInfo;
}
public User getUser(String username){
	User user = null;
	Transaction trns = null;
	Session session = null;
	session = HibernateUtil.openSession();
	try {
		trns = session.beginTransaction();
		Query query = session.createQuery("FROM User WHERE UserName= :userName");
		query.setParameter("userName", username);
		user = (User) query.uniqueResult();
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
	return user;
	
}

	@SuppressWarnings("unchecked")
	public List<User> getUsers(String userName, String password) {
		List<User> users = null;
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery("FROM User WHERE UserName= :userName AND Password= :password");
			query.setParameter("userName", userName);
			query.setParameter("password", password);
			users = (List<User>) query.list();
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
		if (users.toString() == "[]")
			return null;
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUserRole(){
		List<Object[]> list = new ArrayList<Object[]>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "select u.userName ,ur.name from User u,UserRole ur where u.userId = ur.userId";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			list = (List<Object[]>)query.list();
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
		
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<String[]> getAllUser(){
		List<String[]> list = new ArrayList<>();
		Transaction trns = null;
		Session session = null;
		session = HibernateUtil.openSession();
		String querySQL = "select userName ,userId from User";
		try {
			trns = session.beginTransaction();
			Query query = session.createQuery(querySQL);
			list = (List<String[]>)query.list();
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
}
