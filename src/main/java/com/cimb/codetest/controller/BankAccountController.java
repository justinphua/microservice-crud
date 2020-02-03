package com.cimb.codetest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cimb.codetest.bean.BankAccount;
import com.cimb.codetest.common.WSResponseBean;
import com.cimb.codetest.services.BankAccountService;
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
public class BankAccountController {

	@Autowired 
	BankAccountService bankAccountService;
	
	@GetMapping("/customer/{custId}/bankAccount")
	public WSResponseBean getBankAccountByCustomerId(@Min(1) @PathVariable (value = "custId") Long custId) {
		return bankAccountService.getBankAccountByCustId(custId);
	}
	
	@PostMapping("/customer/{custId}/bankAccount")
	public WSResponseBean createNewBankAccount(@PathVariable (value = "custId") Long custId, 
			@Valid @RequestBody BankAccount accountInfo) {
		return bankAccountService.createBankAccount(accountInfo, Constants.USER_ID, custId);
	}
	
	@PutMapping("/customer/{custId}/bankAccount/{accountId}")
	public WSResponseBean updateBankAccount(@PathVariable (value = "custId") Long custId,
			@PathVariable (value = "accountId") Long accountId,
			@Valid @RequestBody BankAccount accountInfo) {
		return bankAccountService.putBankAccount(custId, accountId, accountInfo, Constants.USER_ID);
	}
	
	@PatchMapping("/customer/{custId}/bankAccount/{accountId}")
	public WSResponseBean patchBankAccount(@PathVariable (value = "custId") Long custId,
			@PathVariable (value = "accountId") Long accountId,
			@RequestBody Map<String, Object>accountInfo) {
		return bankAccountService.patchBankAccount(custId, accountId, accountInfo, Constants.USER_ID);
	}
	
	@DeleteMapping("/customer/{custId}/bankAccount/{accountId}")
	public WSResponseBean deleteBankAccount(@PathVariable (value = "custId") Long custId,
			@PathVariable (value = "accountId") Long accountId) {
		return bankAccountService.deleteBankAccount(custId, accountId, Constants.USER_ID);
	}
	
}
