package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public Optional<Purchase> get(Long id) {
        return repository.findById(id);
    }

    public void update(Purchase purchase) {
        repository.save(purchase);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Purchase> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Purchase> list(Pageable pageable, Specification<Purchase> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Purchase> allPurchases() {
        return repository.findAll();
    }

    public void save(Purchase purchase) {
        repository.save(purchase);
    }

    public List<Purchase> clientPurchases(Long clientId) {
        return repository.findPurchasesByClientId(clientId);
    }

    public boolean isExist(Long purchaseId) {
        return repository.existsById(purchaseId);
    }

    public List<Purchase> allPurchaseForSupplier(Long supplierId){
        return repository.findPurchasesBySupplierId(supplierId);
    }
}
