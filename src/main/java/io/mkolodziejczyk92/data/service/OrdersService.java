package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Purchase;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    private final OrdersRepository repository;

    public OrdersService(OrdersRepository repository) {
        this.repository = repository;
    }

    public Optional<Purchase> get(Long id) {
        return repository.findById(id);
    }

    public Purchase update(Purchase entity) {
        return repository.save(entity);
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

    public List<Purchase> purchasesList() {
        return repository.findAll();
    }
}
