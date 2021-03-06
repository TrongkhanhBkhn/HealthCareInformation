package vn.com.daisy.dataelement;
// Generated Nov 20, 2015 12:54:09 AM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DataElementCategory generated by hbm2java
 */
@Entity
@Table(name="DataElementCategory"
    ,catalog="HealthInformationSystem"
)
public class DataElementCategory  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1951042079357624674L;
	private int categoryId;
     private String name;
     private String uid;
     private String code;
     private Date lastUpdated;
     private Date created;
     private Integer userId;
     private String publicAccess;

    public DataElementCategory() {
    }

	
    public DataElementCategory(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
    public DataElementCategory(int categoryId, String name, String uid, String code, Date lastUpdated, Date created, Integer userId, String publicAccess) {
       this.categoryId = categoryId;
       this.name = name;
       this.uid = uid;
       this.code = code;
       this.lastUpdated = lastUpdated;
       this.created = created;
       this.userId = userId;
       this.publicAccess = publicAccess;
    }
   
     @Id 

    
    @Column(name="CategoryID", unique=true, nullable=false)
    public int getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    
    @Column(name="Name", nullable=false, length=160)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="UID", length=11)
    public String getUid() {
        return this.uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }

    
    @Column(name="Code", length=50)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LastUpdated", length=19)
    public Date getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Created", length=19)
    public Date getCreated() {
        return this.created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }

    
    @Column(name="UserID")
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    @Column(name="PublicAccess", length=8)
    public String getPublicAccess() {
        return this.publicAccess;
    }
    
    public void setPublicAccess(String publicAccess) {
        this.publicAccess = publicAccess;
    }




}


