package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Invoice;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
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
}
