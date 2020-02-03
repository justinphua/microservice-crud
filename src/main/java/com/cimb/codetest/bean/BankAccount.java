package com.cimb.codetest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.Where;
import com.cimb.codetest.common.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "BANK_ACCT")
@Where(clause = "IS_DELETE = 'N'")
public class BankAccount extends CommonField  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7372871884884326302L;

	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "Account Number")
	@Column(name = "ACCT_NUM")
	private String accountNumber;
	
	@DecimalMin("1.00")
	@Column(name = "ACCT_BAL")
	private double accountBalance;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUST_ID")
	private CustomerProfile customer;
	
	public BankAccount() {
		
	}
}
