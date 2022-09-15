package com.codebrothers.bank.core.balance.model.repository;

import com.codebrothers.bank.core.balance.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {



}
