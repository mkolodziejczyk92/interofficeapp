package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
    List<Purchase> findPurchasesByClientId(Long clientId);
}
