package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;
    private final ClientService clientService;
    private final PurchaseService purchaseService;

    public InvoiceService(InvoiceRepository repository, ClientService clientService, PurchaseService purchaseService) {
        this.repository = repository;
        this.clientService = clientService;
        this.purchaseService = purchaseService;
    }

    public Optional<Invoice> get(Long id) {
        return repository.findById(id);
    }

    public Invoice update(Invoice entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Invoice> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Invoice> list(Pageable pageable, Specification<Invoice> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Invoice> allInvoices() {
        return repository.findAll();
    }

    public List<Invoice> clientInvoices(Long clientId) {
        return repository.findInvoiceByClientId(clientId);
    }

    public void save(Invoice invoice, Long clientId) {
        invoice.setGrossAmount(purchaseService.countGrossValue(invoice.getVat().replace("%", ""), invoice.getNetAmount()));
        invoice.setClient(clientService.get(clientId).orElseThrow());
        BigDecimal netInput = new BigDecimal(invoice.getNetAmount().replace(',', '.'));
        BigDecimal netInputAfterScale = netInput.setScale(2, RoundingMode.HALF_UP);
        invoice.setNetAmount(String.valueOf(netInputAfterScale).replace('.', ','));
        repository.save(invoice);
    }
}
