package vn.com.daisy.user;
// Generated Oct 16, 2015 10:39:47 PM by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserMembership generated by hbm2java
 */
@Entity
@Table(name="UserMembership"
    ,catalog="HealthInformationSystem"
)
public class UserMembership  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 3438748340507124961L;
	private UserMembershipId id;

    public UserMembership() {
    }

    public UserMembership(UserMembershipId id) {
       this.id = id;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="organisationUnitId", column=@Column(name="OrganisationUnitID", nullable=false) ), 
        @AttributeOverride(name="userRoleId", column=@Column(name="UserRoleID", nullable=false) ) } )
    public UserMembershipId getId() {
        return this.id;
    }
    
    public void setId(UserMembershipId id) {
        this.id = id;
    }




}

