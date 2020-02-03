package com.cimb.codetest.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cimb.codetest.bean.CustomerProfile;
import com.cimb.codetest.common.Constants;
import com.cimb.codetest.common.ResourceNotFoundException;
import com.cimb.codetest.common.WSResponseBean;
import com.cimb.codetest.common.WebServiceUtils;
import com.cimb.codetest.dao.CustomerProfileRepository;
import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CustomerProfileService {
	@Autowired
	private CustomerProfileRepository customerProfileRepo;
	
	public WSResponseBean getCustomertProfile() {
		List<CustomerProfile> clientProfile;
		WSResponseBean res = null;
		
		try {
			clientProfile = customerProfileRepo.findAll();
			
			if(clientProfile != null) {
				res = WebServiceUtils.getInstance().commonSuccess(clientProfile);
			} else {
				res = WebServiceUtils.getInstance().commonSuccess(Constants.APP_ERR_RECORD_NOT_FOUND_MSG);
			}
			
		} catch (Exception e) {
			System.out.println("CustomerProfileService.getCustomertProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		
		return res;
	}
	
	public WSResponseBean getCustomertProfileById(long custId) {
		Optional<CustomerProfile> clientProfile;
		WSResponseBean res = null;
		
		try {
			clientProfile = customerProfileRepo.findById(custId);

			if(clientProfile != null) {
				res = WebServiceUtils.getInstance().commonSuccess(clientProfile);
			} else {
				res = WebServiceUtils.getInstance().commonSuccess(Constants.APP_ERR_RECORD_NOT_FOUND_MSG);
			}
		} catch (Exception e) {
			System.out.println("CustomerProfileService.getCustomertProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		
		return res;
	}
	
	public WSResponseBean createNewCustomerProfile(CustomerProfile customerInfo, String userId) {
		WSResponseBean res = null;
		
		try {
			res = saveCustomerProfile(customerInfo, userId, Constants.ACTION_NEW);
		} catch (Exception e) {
			System.out.println("ClientProfileService.putCustomerProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
	public WSResponseBean putCustomerProfile(long custId, CustomerProfile customerInfo, String userId) {
		WSResponseBean res = null;
		CustomerProfile customer = null;
		
		try {
			customer = customerProfileRepo.findById(custId)
					.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			customer.setFirstName(customerInfo.getFirstName());
			customer.setLastName(customerInfo.getLastName());
			customer.setEmail(customerInfo.getEmail());
			customer.setMailingAddress1(customerInfo.getMailingAddress1());
			customer.setMailingAddress2(customerInfo.getMailingAddress2());
			customer.setPhoneNumber(customerInfo.getPhoneNumber());
			
			res = saveCustomerProfile(customer, userId, Constants.ACTION_UPDATE);
			
		} catch (Exception e) {
			System.out.println("ClientProfileService.putCustomerProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
	public WSResponseBean patchCustomerProfile(long custId, Map<String, Object> updates, String userId) {
		WSResponseBean res = null;
		CustomerProfile customer = null;
		
		try {
			customer = customerProfileRepo.findById(custId)
					.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			customer = mapToObject(updates, customer);
			
			res = saveCustomerProfile(customer, userId, Constants.ACTION_UPDATE);
			
		} catch (Exception e) {
			System.out.println("ClientProfileService.patchCustomerProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
		
	}
	
	public WSResponseBean deleteCustomerProfile(long custId, String userId) {
		WSResponseBean res = null;
		CustomerProfile customer = null;
		
		try {
			customer = customerProfileRepo.findById(custId)
					.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			res = saveCustomerProfile(customer, userId, Constants.ACTION_DELETE);
		} catch (Exception e) {
			System.out.println("ClientProfileService.deleteCustomerProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
	private CustomerProfile formatCustomerProfile(CustomerProfile customerInfo, String userId, int mode) {
		CustomerProfile customer = null;
		
		try {
			customer = (CustomerProfile) customerInfo.clone();
			if(mode == Constants.ACTION_NEW) {
				customer.setCreatedBy(userId);
				customer.setCreationTimestamp(new Date());
				customer.setIsActive(Constants.STR_YES);
				customer.setIsDelete(Constants.STR_NO);
			}
			
			if(mode == Constants.ACTION_DELETE) {
				customer.setIsDelete(Constants.STR_YES);
			}
			
			customer.setUpdatedBy(userId);
			customer.setUpdateTimestamp(new Date());
		} catch(Exception e) {
			System.out.println("ClientProfileService.formatCustomerProfile: Error -> " + e.getMessage());
		}
		
		return customer;
	}
	
	private CustomerProfile mapToObject(Map<String, Object> updates, CustomerProfile customerInfo) {
		CustomerProfile customer = (CustomerProfile) customerInfo.clone();
		
		updates.forEach((k, v) -> {
	        Field field = ReflectionUtils.findField(CustomerProfile.class, k);
	        field.setAccessible(true);
	        ReflectionUtils.setField(field, customer, v);
	    });
	    
		return customer;
	}
	
	public WSResponseBean saveCustomerProfile(CustomerProfile customerInfo, String userId, int mode) {
		WSResponseBean res = null;
		CustomerProfile customer;
		
		try {
			customer = formatCustomerProfile(customerInfo, userId, mode);
			customer = customerProfileRepo.save(customer);
			res = WebServiceUtils.getInstance().commonSuccess(customer);
		} catch (Exception e) {
			System.out.println("ClientProfileService.saveCustomerProfile: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
}
