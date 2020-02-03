package com.cimb.codetest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cimb.codetest.bean.CustomerProfile;
import com.cimb.codetest.common.WSResponseBean;
import com.cimb.codetest.services.CustomerProfileService;
import com.cimb.codetest.common.Constants;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CustomerProfileController {

	@Autowired 
	CustomerProfileService customerProfileService;
	
	@GetMapping("/customer")
	public WSResponseBean getCustomerList() {
		return customerProfileService.getCustomertProfile();
	}
	
	@GetMapping("/customer/{custId}")
	public WSResponseBean getCustomerList(@Min(1) @PathVariable (value = "custId") Long custId) {
		return customerProfileService.getCustomertProfileById(custId);
	}
	
	@PostMapping("/customer")
	public WSResponseBean createNewCustomer(@Valid @RequestBody CustomerProfile customerInfo) {
		return customerProfileService.createNewCustomerProfile(customerInfo, Constants.USER_ID);
	}
	
	@PutMapping("/customer/{custId}")
	public WSResponseBean updateCustomer(@Min(1) @PathVariable (value = "custId") Long custId, 
			@Valid @RequestBody CustomerProfile customerInfo) {
		return customerProfileService.putCustomerProfile(custId, customerInfo, Constants.USER_ID);
	}
	
	@PatchMapping("/customer/{custId}")
	public WSResponseBean patchCustomer(@Min(1) @PathVariable (value = "custId") Long custId, 
			@RequestBody Map<String, Object>customerInfo) {
		return customerProfileService.patchCustomerProfile(custId, customerInfo, Constants.USER_ID);
	}
	
	@DeleteMapping("/customer/{custId}")
	public WSResponseBean deleteCustomer(@Min(1) @PathVariable (value = "custId") Long custId) {
		return customerProfileService.deleteCustomerProfile(custId, Constants.USER_ID);
	}
	
}
