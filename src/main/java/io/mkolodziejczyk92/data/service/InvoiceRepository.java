package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    List<Invoice> findInvoiceByClientId(Long clientId);
    List<Invoice> findInvoiceByPurchaseId(Long purchaseId);
}
