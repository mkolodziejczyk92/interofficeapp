package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

   Optional<Contract> findByNumber(String contractNumber);
}
