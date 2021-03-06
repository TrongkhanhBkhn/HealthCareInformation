package vn.com.daisy.dataelement;
// Generated Nov 20, 2015 12:54:09 AM by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CategoryCategoryOption generated by hbm2java
 */
@Entity
@Table(name="Category_CategoryOption"
    ,catalog="HealthInformationSystem"
)
public class CategoryCategoryOption  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 2277013877720426888L;
	private CategoryCategoryOptionId id;
     private int categoryOptionId;

    public CategoryCategoryOption() {
    }

    public CategoryCategoryOption(CategoryCategoryOptionId id, int categoryOptionId) {
       this.id = id;
       this.categoryOptionId = categoryOptionId;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="categoryId", column=@Column(name="CategoryID", nullable=false) ), 
        @AttributeOverride(name="sortOrder", column=@Column(name="Sort_Order", nullable=false) ) } )
    public CategoryCategoryOptionId getId() {
        return this.id;
    }
    
    public void setId(CategoryCategoryOptionId id) {
        this.id = id;
    }

    
    @Column(name="CategoryOptionID", nullable=false)
    public int getCategoryOptionId() {
        return this.categoryOptionId;
    }
    
    public void setCategoryOptionId(int categoryOptionId) {
        this.categoryOptionId = categoryOptionId;
    }




}


