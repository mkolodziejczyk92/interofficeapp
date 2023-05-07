package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

    List<Contract> findContractsByClientId(Long clientId);

    @Query(value = """   
           SELECT COUNT(number)
           FROM contract
           WHERE  number LIKE CONCAT('%/%',:month,'/',:year);
            """, nativeQuery = true)
    int findThisMonthContractsQuantity(@Param("month") int month, @Param("year") int year);
}
