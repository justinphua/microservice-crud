package com.cimb.codetest.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class CommonField implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451467971293997828L;

	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "IS_DELETE")
	private String isDelete; 
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TMS")
	private Date creationTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TMS")
	private Date updateTimestamp;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	 
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("CloneNotSupportedException comes out : " + e.getMessage());
			return null;
		} 
	}
}
