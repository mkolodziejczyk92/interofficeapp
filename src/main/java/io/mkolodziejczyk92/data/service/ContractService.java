package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Contract;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private final ContractRepository repository;

    public ContractService(ContractRepository repository) {
        this.repository = repository;
    }

    public Optional<Contract> get(Long id) {
        return repository.findById(id);
    }

    public Contract update(Contract entity) {
        return repository.save(entity);
    }

    public List<Contract> contractList(){
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Contract> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Contract> list(Pageable pageable, Specification<Contract> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
