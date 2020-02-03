package com.cimb.codetest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cimb.codetest.bean.CustomerProfile;


@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long>  {
}