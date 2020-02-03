package com.cimb.codetest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cimb.codetest.bean.BankAccount;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>  {

	List<BankAccount> findByCustomerId(Long custId);
}