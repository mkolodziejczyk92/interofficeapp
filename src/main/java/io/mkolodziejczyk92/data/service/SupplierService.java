package io.mkolodziejczyk92.data.service;


import io.mkolodziejczyk92.data.entity.Supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public Optional<Supplier> get(Long id) {
        return repository.findById(id);
    }

    public Supplier saveNewSupplier(Supplier entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Supplier> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Supplier> list(Pageable pageable, Specification<Supplier> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Supplier> supplierList() {
        return repository.findAll();
    }
}
