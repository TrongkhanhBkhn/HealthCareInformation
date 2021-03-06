package vn.com.daisy.Period;
// Generated Oct 16, 2015 10:05:36 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Period generated by hbm2java
 */
@Entity
@Table(name="Period"
    ,catalog="HealthInformationSystem"
)
public class Period  implements java.io.Serializable {
     /**
	 * 
	 */
	private static final long serialVersionUID = -549600243035600265L;
	private int periodId;
     private PeriodType periodType;
     private Date startDate;
     private Date endDate;

    public Period() {
    }

    public Period(int periodId, PeriodType periodType, Date startDate, Date endDate) {
       this.periodId = periodId;
       this.periodType = periodType;
       this.startDate = startDate;
       this.endDate = endDate;
    }
   
     @Id 

    
    @Column(name="PeriodID", unique=true, nullable=false)
    public int getPeriodId() {
        return this.periodId;
    }
    
    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PeriodTypeID", nullable=false)
    public PeriodType getPeriodType() {
        return this.periodType;
    }
    
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="StartDate", nullable=false, length=10)
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="EndDate", nullable=false, length=10)
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }




}


