package com.cimb.codetest.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;

import com.cimb.codetest.common.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "CUST_PROFILE")
@Where(clause = "IS_DELETE = 'N'")
public class CustomerProfile extends CommonField  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2608936960530906588L;

	@Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "First Name")
	@Column(name = "FIRST_NM")
	private String firstName;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "Last Name")
	@Column(name = "LAST_NM")
	private String lastName;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "Email Address")
	@Email
	@Column(name = "EMAIL")
	private String email;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "Phone Number")
	@Column(name = "PHONE_NUM")
	private String phoneNumber;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "Mailing Address Line 1")
	@Column(name = "MAIL_ADDR_1")
	private String mailingAddress1;
	
	@NotEmpty(message = Constants.VALIDATE_MANDATORY_MSG + "Mailing Address Line 2")
	@Column(name = "MAIL_ADDR_2")
	private String mailingAddress2;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "customer")
	private List<BankAccount> bankAccount;
	
	public CustomerProfile() {
    }
}
