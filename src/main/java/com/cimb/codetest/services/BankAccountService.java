package com.cimb.codetest.services;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.cimb.codetest.bean.BankAccount;
import com.cimb.codetest.bean.CustomerProfile;
import com.cimb.codetest.common.Constants;
import com.cimb.codetest.common.WSResponseBean;
import com.cimb.codetest.common.WebServiceUtils;
import com.cimb.codetest.dao.BankAccountRepository;
import com.cimb.codetest.dao.CustomerProfileRepository;
import com.cimb.codetest.common.ResourceNotFoundException;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepo;
	
	@Autowired
	private CustomerProfileRepository customerProfileRepo;
	
	public WSResponseBean getBankAccountByCustId(long custId) { 
		List<BankAccount> bankAccount;
		WSResponseBean res = null;
		
		try {
			bankAccount = bankAccountRepo.findByCustomerId(custId);
			res = WebServiceUtils.getInstance().commonSuccess(bankAccount);
			
			
		} catch (Exception e) {
			System.out.println("BankAccountService.getBankAccount: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		
		return res;
	}
	
	public WSResponseBean createBankAccount(BankAccount accountInfo, String userId, long custId) {
		WSResponseBean res = null;
		CustomerProfile customer;
		
		try {
			
			customer =  customerProfileRepo.findById(custId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			accountInfo.setCustomer(customer);
			res = saveBankAccount(accountInfo, userId, Constants.ACTION_NEW);
		} catch (Exception e) {
			System.out.println("BankAccountService.createBankAccount: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
	public WSResponseBean putBankAccount(long custId, long accountId, BankAccount accountInfo, String userId) {
		WSResponseBean res = null;
		BankAccount account;
		
		try {
			customerProfileRepo.findById(custId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			account = bankAccountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Account Id: " + accountId));
	
			account.setAccountNumber(accountInfo.getAccountNumber());
			account.setAccountBalance(accountInfo.getAccountBalance());
			
			res = saveBankAccount(account, userId, Constants.ACTION_UPDATE);
			
		} catch (Exception e) {
			System.out.println("BankAccountService.putBankAccount: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
	public WSResponseBean patchBankAccount(long custId, long accountId, Map<String, Object> updates, String userId) {
		WSResponseBean res = null;
		BankAccount account;
		
		try {
			customerProfileRepo.findById(custId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			account = bankAccountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Account Id: " + accountId));
		
			
			account = mapToObject(updates, account);
			res = saveBankAccount(account, userId, Constants.ACTION_UPDATE);
			
		} catch (Exception e) {
			System.out.println("BankAccountService.patchBankAccount: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
		
	}
	
	public WSResponseBean deleteBankAccount(long custId, long accountId, String userId) {
		WSResponseBean res = null;
		BankAccount account;
		
		try {
			customerProfileRepo.findById(custId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Customer Id: " + custId));
			
			account = bankAccountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.APP_ERR_RECORD_NOT_FOUND_MSG + ": Account Id: " + accountId));
			
			res = saveBankAccount(account, userId, Constants.ACTION_DELETE);
		} catch (Exception e) {
			System.out.println("BankAccountService.deleteBankAccount: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
	private BankAccount formatBankAccount(BankAccount accountInfo, String userId, int mode) {
		BankAccount account = null;
				
		try {
			account = (BankAccount) accountInfo.clone();
			if(mode == Constants.ACTION_NEW) {
				account.setCreatedBy(userId);
				account.setCreationTimestamp(new Date());
				account.setIsActive(Constants.STR_YES);
				account.setIsDelete(Constants.STR_NO);
			}
			
			if(mode == Constants.ACTION_DELETE) {
				account.setIsDelete(Constants.STR_YES);
			}
			
			account.setUpdatedBy(userId);
			account.setUpdateTimestamp(new Date());
		} catch(Exception e) {
			System.out.println("BankAccountService.formatBankAccount: Error -> " + e.getMessage());
		}
		return account;
	}
	
	private BankAccount mapToObject(Map<String, Object> updates, BankAccount accountInfo) {
		BankAccount account = (BankAccount) accountInfo.clone();
		
		updates.forEach((k, v) -> {
	        Field field = ReflectionUtils.findField(BankAccount.class, k);
	        field.setAccessible(true);
	        ReflectionUtils.setField(field, account, v);
	    });
	    
		return account;
	}
	
	public WSResponseBean saveBankAccount(BankAccount accountInfo, String userId, int mode) {
		WSResponseBean res = null;
		BankAccount account;
		
		try {
			account = formatBankAccount(accountInfo, userId, mode);
			account = bankAccountRepo.save(account);
			res = WebServiceUtils.getInstance().commonSuccess(account);
		} catch (Exception e) {
			System.out.println("BankAccountService.saveBankAccount: Error -> " + e.getMessage());
			res = WebServiceUtils.getInstance().commonError(null, e.getMessage());
		}
		return res;
	}
	
}
