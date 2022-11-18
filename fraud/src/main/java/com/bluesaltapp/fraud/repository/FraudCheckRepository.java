package com.bluesaltapp.fraud.repository;

import com.bluesaltapp.fraud.model.FraudCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudCheckRepository extends JpaRepository<FraudCheck, Integer> {
}
