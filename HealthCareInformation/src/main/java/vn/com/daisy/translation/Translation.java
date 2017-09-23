package vn.com.daisy.translation;
// Generated Oct 17, 2015 4:37:49 PM by Hibernate Tools 3.6.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Translation generated by hbm2java
 */
@Entity
@Table(name="Translation"
    ,catalog="HealthInformationSystem"
)
public class Translation  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1547179571639044618L;

	private int translationId;
     private String objectClass;
     private String objectProperty;
     private Integer objectId;
     private String objectUid;
     private String code;
     private String locale;
     private String value;
     private Date lastUpdated;
     private Date created;

    public Translation() {
    }

	
    public Translation(int translationId, String objectClass, String locale, String value) {
        this.translationId = translationId;
        this.objectClass = objectClass;
        this.locale = locale;
        this.value = value;
    }
    public Translation(int translationId, String objectClass, String objectProperty, Integer objectId, String objectUid, String code, String locale, String value, Date lastUpdated, Date created) {
       this.translationId = translationId;
       this.objectClass = objectClass;
       this.objectProperty = objectProperty;
       this.objectId = objectId;
       this.objectUid = objectUid;
       this.code = code;
       this.locale = locale;
       this.value = value;
       this.lastUpdated = lastUpdated;
       this.created = created;
    }
   
     @Id 

    
    @Column(name="TranslationID", unique=true, nullable=false)
    public int getTranslationId() {
        return this.translationId;
    }
    
    public void setTranslationId(int translationId) {
        this.translationId = translationId;
    }

    
    @Column(name="ObjectClass", nullable=false, length=63)
    public String getObjectClass() {
        return this.objectClass;
    }
    
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    
    @Column(name="ObjectProperty", length=65535)
    public String getObjectProperty() {
        return this.objectProperty;
    }
    
    public void setObjectProperty(String objectProperty) {
        this.objectProperty = objectProperty;
    }

    
    @Column(name="ObjectID")
    public Integer getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    
    @Column(name="ObjectUID", length=11)
    public String getObjectUid() {
        return this.objectUid;
    }
    
    public void setObjectUid(String objectUid) {
        this.objectUid = objectUid;
    }

    
    @Column(name="Code", length=50)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="Locale", nullable=false, length=15)
    public String getLocale() {
        return this.locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }

    
    @Column(name="Value", nullable=false, length=65535)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
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




}


